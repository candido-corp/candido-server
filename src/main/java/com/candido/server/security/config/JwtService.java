package com.candido.server.security.config;

import com.candido.server.config.ConfigAppProperties;
import com.candido.server.domain.v1.token.EnumJwtExceptionState;
import com.candido.server.exception._common.BTExceptionResolver;
import com.candido.server.exception.security.auth.ExceptionVerifyRegistrationToken;
import com.candido.server.exception.security.jwt.ExceptionInvalidJWTToken;
import com.candido.server.exception.security.jwt.ExceptionSecurityJWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Provides services for managing JWTs including creating, parsing, and validating tokens.
 */
@Slf4j
@Service
public class JwtService {

    @Autowired
    private BTExceptionResolver btExceptionResolver;

    @Autowired
    private ConfigAppProperties configAppProperties;

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token from which to extract the username.
     * @return the username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the username from the access token and verifies it.
     *
     * @param accessToken the access token to verify.
     * @return the username if extraction is successful and valid.
     * @throws ExceptionVerifyRegistrationToken if the username is not found in the token.
     */
    public String extractAndValidateUsername(String accessToken) throws ExceptionVerifyRegistrationToken {
        String username = extractUsername(accessToken);
        if (username == null) throw new ExceptionInvalidJWTToken();
        return username;
    }

    /**
     * Extracts a specific claim from the token using a provided claims resolver.
     *
     * @param token the JWT token from which to extract the claim.
     * @param claimsResolver a function to resolve the specific claim from the token.
     * @param <T> the type of the claim to be extracted.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claims == null ? null : claimsResolver.apply(claims);
    }

    /**
     * Generates a registration token for the given user details.
     *
     * @param userDetails the user details for which the registration token is to be generated.
     * @return a string representing the generated registration token.
     */
    public String generateRegistrationToken(UserDetails userDetails) {
        int expirationTimeMillis = configAppProperties.getSecurity().getJwt().getRegistrationToken().getExpiration();
        return buildToken(new HashMap<>(), userDetails, expirationTimeMillis);
    }

    /**
     * Generates a reset token for the given user details.
     *
     * @param userDetails the user details for which the reset token is to be generated.
     * @return a string representing the generated reset token.
     */
    public String generateResetToken(UserDetails userDetails) {
        int expirationTimeMillis = configAppProperties.getSecurity().getJwt().getResetToken().getExpiration();
        return buildToken(new HashMap<>(), userDetails, expirationTimeMillis);
    }

    /**
     * Generates a standard JWT token for the specified user details.
     *
     * @param userDetails the user details for which the token is to be generated.
     * @return a string representing the generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional claims for the specified user details.
     *
     * @param extraClaims additional claims to include in the token.
     * @param userDetails the user details for which the token is to be generated.
     * @return a string representing the generated JWT token.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        int expirationTimeMillis = configAppProperties.getSecurity().getJwt().getExpiration();
        extraClaims.put("roles", userDetails.getAuthorities());
        return buildToken(extraClaims, userDetails, expirationTimeMillis);
    }

    /**
     * Generates a refresh token for the specified user details.
     *
     * @param userDetails the user details for which the refresh token is to be generated.
     * @return a string representing the generated refresh token.
     */
    public String generateRefreshToken(UserDetails userDetails) {
        int expirationTimeMillis = configAppProperties.getSecurity().getJwt().getRefreshToken().getExpiration();
        return buildToken(new HashMap<>(), userDetails, expirationTimeMillis);
    }

    /**
     * Builds a token with specified claims, user details, and expiration.
     *
     * @param extraClaims additional claims to include in the token.
     * @param userDetails the user details to include in the token.
     * @param expirationTimeMillis the expiration time of the token in milliseconds.
     * @return a string representing the JWT token.
     */
    public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expirationTimeMillis) {
        return Jwts
                .builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .setIssuer(configAppProperties.getSecurity().getJwt().getIssuer())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the provided token against the user details.
     *
     * @param token the JWT token to validate.
     * @param userDetails the user details against which the token is validated.
     * @return true if the token is valid; false otherwise.
     */
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the provided token is expired.
     *
     * @param token the JWT token to check for expiration.
     * @return true if the token is expired; false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date of the token.
     *
     * @param token the JWT token from which to extract the expiration date.
     * @return the expiration date of the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the provided JWT token.
     *
     * @param token the JWT token from which to extract the claims.
     * @return the claims contained within the token.
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException ex) {
            handleJwtException(ex, token);
            return null;
        } catch (IllegalArgumentException ex) {
            handleIllegalArgumentException(ex, token);
            return null;
        }
    }

    /**
     * Handles IllegalArgumentException specifically for token parsing issues.
     *
     * @param ex the IllegalArgumentException that was caught.
     * @param token the token that caused the exception.
     */
    private void handleIllegalArgumentException(IllegalArgumentException ex, String token) {
        btExceptionResolver.resolveAuthBTException(
                EnumJwtExceptionState.TOKEN_NULL_EMPTY_OR_WHITESPACE.name(), new ExceptionSecurityJWT(ex.getMessage()), token
        );
    }

    /**
     * Handles JWT exceptions by categorizing them and throwing specific application exceptions.
     *
     * @param ex the JWT exception that was caught.
     * @param token the token that caused the exception.
     */
    private void handleJwtException(JwtException ex, String token) {
        String jwtState = determineJwtExceptionState(ex);
        btExceptionResolver.resolveAuthBTException(
                jwtState, new ExceptionSecurityJWT(ex.getMessage()), token
        );
    }

    /**
     * Determines the state of the JWT based on the exception type.
     *
     * @param ex the JWT exception to evaluate.
     * @return the state of the JWT as a string.
     */
    private String determineJwtExceptionState(JwtException ex) {
        if (ex instanceof ExpiredJwtException) {
            return EnumJwtExceptionState.JWT_EXPIRED.name();
        } else if (ex instanceof MalformedJwtException) {
            return EnumJwtExceptionState.JWT_INVALID.name();
        } else if (ex instanceof UnsupportedJwtException) {
            return EnumJwtExceptionState.JWT_NOT_SUPPORTED.name();
        } else if (ex instanceof SignatureException) {
            return EnumJwtExceptionState.SIGNATURE_VALIDATION_FAILED.name();
        }
        return EnumJwtExceptionState.UNKNOWN.name();
    }

    /**
     * Retrieves the signing key used to verify the JWT token.
     *
     * @see <a href="https://www.allkeysgenerator.com/">Generatore di chiave</a>
     * @return the secret key used to sign the JWT token.
     */
    private SecretKey getSignInKey() {
        String secretKey = configAppProperties.getSecurity().getJwt().getSecretKey();
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
