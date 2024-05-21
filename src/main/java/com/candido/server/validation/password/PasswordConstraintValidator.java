package com.candido.server.validation.password;

import com.candido.server.exception.account.ExceptionInvalidPasswordAccount;
import com.candido.server.exception.account.ExceptionInvalidPasswordAccountList;
import org.passay.*;

import java.util.ArrayList;
import java.util.List;

public class PasswordConstraintValidator {

    public static void isValid(String password) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8, 16));
        rules.add(new WhitespaceRule());
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Special, 1));

        PasswordValidator validator = new PasswordValidator(rules);
        RuleResult result = validator.validate(new PasswordData(password));

        List<ExceptionInvalidPasswordAccount> exceptions = new ArrayList<>();
        result.getDetails().forEach(ruleResultDetail -> {
            exceptions.add(
                    new ExceptionInvalidPasswordAccount(
                            ruleResultDetail.getErrorCode(),
                            ruleResultDetail.getValues(),
                            ruleResultDetail.getParameters()
                    )
            );
        });

        if(!result.isValid()) throw new ExceptionInvalidPasswordAccountList(exceptions);
    }

}