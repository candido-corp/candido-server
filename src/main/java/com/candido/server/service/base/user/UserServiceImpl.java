package com.candido.server.service.base.user;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.user.Gender;
import com.candido.server.domain.v1.user.User;
import com.candido.server.domain.v1.user.UserRepository;
import com.candido.server.domain.v1.user.User_;
import com.candido.server.dto.v1.request.account.RequestUpdateUserDto;
import com.candido.server.dto.v1.request.geo.RequestAddressDto;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionAccountNotFound;
import com.candido.server.service.base.geo.AddressService;
import com.candido.server.service.base.geo.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            AddressService addressService
    ) {
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    @Override
    public Optional<User> findUserByAccountId(int accountId) {
        Specification<User> byAccountId = ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(User_.ACCOUNT_ID), accountId));
        return userRepository.findOne(byAccountId);
    }

    @Override
    public User findUserByAccountIdOrThrow(int accountId) {
        return findUserByAccountId(accountId)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ERROR_BUSINESS_USER_NOT_FOUND.name()));
    }

    @Override
    public User save(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User save(User user, RequestUpdateUserDto requestUpdateUserDto, boolean canChangeName) {
        if(canChangeName) {
            user.setFirstName(requestUpdateUserDto.firstName());
            user.setLastName(requestUpdateUserDto.lastName());
            user.setLastModifiedName(LocalDateTime.now());
        }

        user.setGenderId(requestUpdateUserDto.genderId());
        user.setBirthdate(requestUpdateUserDto.birthdate());
        user.setMobileNumber(requestUpdateUserDto.mobileNumber());
        user.setPhoneNumber(requestUpdateUserDto.phoneNumber());

        if (requestUpdateUserDto.address() != null) {
            Address address = addressService.saveAddress(
                    user.getAddressId(), requestUpdateUserDto.address()
            );
            user.setAddressId(address.getAddressId());
        }

        save(user);
        return findUserByAccountIdOrThrow(user.getAccountId());
    }

    @Override
    public User updateAddress(User user, RequestAddressDto requestUserAddressDto) {
        if (requestUserAddressDto != null) {
            Address address = addressService.saveAddress(
                    user.getAddressId(), requestUserAddressDto
            );
            if (address != null) {
                user.setAddressId(address.getAddressId());
                save(user);
            }
        }
        return findUserByAccountIdOrThrow(user.getAccountId());
    }

    @Override
    public void deleteAddress(User user) {
        if (user.getAddressId() != null) {
            addressService.deleteAddress(user.getAddressId());
            user.setAddressId(null);
            save(user);
        }
    }

}
