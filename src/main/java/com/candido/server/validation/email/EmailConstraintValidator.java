package com.candido.server.validation.email;

import java.util.regex.Pattern;

public class EmailConstraintValidator {

    private EmailConstraintValidator() {}

    public static boolean isValid(String email) {
        String patternRFC5322 = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.compile(patternRFC5322)
                .matcher(email)
                .matches();
    }

}
