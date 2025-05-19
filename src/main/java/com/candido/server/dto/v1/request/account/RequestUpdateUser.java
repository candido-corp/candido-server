package com.candido.server.dto.v1.request.account;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception.account.ExceptionInvalidPasswordAccount;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record RequestUpdateUser(
        @JsonProperty("first_name")
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_FIRST_NAME_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_FIRST_NAME}
        )
        String firstName,

        @JsonProperty("last_name")
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_LAST_NAME_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_LAST_NAME}
        )
        String lastName,

        @JsonProperty("gender_id") Integer genderId,
        @JsonProperty("birthdate") LocalDate birthdate,
        @JsonProperty("mobile_number") String mobileNumber,
        @JsonProperty("phone_number") String phoneNumber
) {
    public static final String JSON_PROPERTY_FIRST_NAME = "first_name";
    public static final String JSON_PROPERTY_LAST_NAME = "last_name";
}
