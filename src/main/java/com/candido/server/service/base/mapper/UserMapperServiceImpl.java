package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.user.Gender;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.util.GenderDto;
import com.candido.server.dto.v1.util.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapperServiceImpl implements UserMapperService {

    private final AddressMapperService addressMapperService;

    @Autowired
    public UserMapperServiceImpl(
            AddressMapperService addressMapperService
    ) {
        this.addressMapperService = addressMapperService;
    }

    @Override
    public UserDto userToUserDto(User user, boolean hasOpenApplications) {
        if (user == null) return null;

        UserDto userDto = new UserDto();

        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(account.getEmail());
        userDto.setGender(genderToGenderDto(user.getGender()));
        userDto.setAddress(addressMapperService.addressToUserAddressDto(user.getAddress()));
        userDto.setBirthdate(user.getBirthdate());
        userDto.setMobileNumber(user.getMobileNumber());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setCanChangeName(user.getLastModifiedName(), hasOpenApplications);

        return userDto;
    }

    protected GenderDto genderToGenderDto(Gender gender) {
        if (gender == null) return null;
        return new GenderDto(
                gender.getId(),
                gender.getDescription()
        );
    }

}
