package com.candido.server.validation.password;

import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.exception.account.ExceptionInvalidPasswordAccount;
import com.candido.server.exception.account.ExceptionInvalidPasswordAccountList;
import org.passay.*;

import java.util.ArrayList;
import java.util.List;

public class PasswordConstraintValidator {

    private PasswordConstraintValidator() {}

    public static CharacterData getCustomInsufficientSpecialCharacterData() {
        return new CharacterData() {
            @Override
            public String getErrorCode() {
                return "INSUFFICIENT_SPECIAL";
            }

            @Override
            public String getCharacters() {
                return "@";
            }
        };
    }

    public static boolean isValid(String password) {
        if(password == null) return false;

        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8, 16));
        rules.add(new WhitespaceRule());
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        rules.add(new CharacterRule(getCustomInsufficientSpecialCharacterData(), 1));

        PasswordValidator validator = new PasswordValidator(rules);
        RuleResult result = validator.validate(new PasswordData(password));

        List<ExceptionInvalidPasswordAccount> exceptions = new ArrayList<>();
        result.getDetails().forEach(ruleResultDetail ->
            exceptions.add(
                    new ExceptionInvalidPasswordAccount(
                            "ERROR_VALIDATION_PASSWORD_" + ruleResultDetail.getErrorCode(),
                            ruleResultDetail.getValues(),
                            ruleResultDetail.getParameters(),
                            List.of(RequestRegister.JSON_PROPERTY_PASSWORD)
                    )
            )
        );

        if(!result.isValid()) throw new ExceptionInvalidPasswordAccountList(exceptions);
        return result.isValid();
    }

}