package com.candido.server.service.base.user;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.Gender;
import com.candido.server.domain.v1.user.User;
import com.candido.server.domain.v1.user.UserRepository;
import com.candido.server.domain.v1.user.User_;
import com.candido.server.dto.v1.request.account.RequestUpdateUser;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionAccountNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GenderService genderService;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            GenderService genderService) {
        this.userRepository = userRepository;
        this.genderService = genderService;
    }

    @Override
    public User getAuthenticatedUser(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        return findUserByAccountIdOrThrow(account.getId());
    }

    @Override
    public Optional<User> findUserByAccountId(Long accountId) {
        Specification<User> byAccountId = ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(User_.ACCOUNT_ID), accountId));
        return userRepository.findOne(byAccountId);
    }

    @Override
    public User findUserByAccountIdOrThrow(Long accountId) {
        return findUserByAccountId(accountId)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ERROR_BUSINESS_USER_NOT_FOUND.name()));
    }

    @Override
    public User save(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User save(User user, RequestUpdateUser requestUpdateUser, boolean canChangeName) {
        if(canChangeName) {
            user.setFirstName(requestUpdateUser.firstName());
            user.setLastName(requestUpdateUser.lastName());
            user.setLastModifiedName(LocalDateTime.now());
        }

        if(requestUpdateUser.genderId() != null) {
            Gender gender = genderService.findGenderByIdOrThrow(requestUpdateUser.genderId());
            user.setGenderId(gender.getId());
        } else user.setGenderId(null);

        user.setBirthdate(requestUpdateUser.birthdate());
        user.setMobileNumber(requestUpdateUser.mobileNumber());
        user.setPhoneNumber(requestUpdateUser.phoneNumber());

        save(user);
        return findUserByAccountIdOrThrow(user.getAccountId());
    }

}
