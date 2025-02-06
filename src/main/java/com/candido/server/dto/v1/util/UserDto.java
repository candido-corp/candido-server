package com.candido.server.dto.v1.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private static final int NAME_CHANGE_TIME_THRESHOLD = 30;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("email")
    String email;

    @JsonProperty("gender")
    GenderDto gender;

    @JsonProperty("address")
    AddressDto address;

    @JsonProperty("birthdate")
    LocalDate birthdate;

    @JsonProperty("mobile_number")
    String mobileNumber;

    @JsonProperty("phone_number")
    String phoneNumber;

    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @JsonProperty("deleted_at")
    LocalDateTime deletedAt;

    @JsonProperty("can_change_name")
    boolean canChangeName;

    public void setCanChangeName(LocalDateTime lastModifiedName) {
        this.canChangeName =
                lastModifiedName == null ||
                        lastModifiedName.plusDays(NAME_CHANGE_TIME_THRESHOLD).isBefore(LocalDateTime.now());
    }
}
