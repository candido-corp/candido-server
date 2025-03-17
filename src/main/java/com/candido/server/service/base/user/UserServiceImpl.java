package com.candido.server.service.base.user;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.user.Gender;
import com.candido.server.domain.v1.user.User;
import com.candido.server.domain.v1.user.UserRepository;
import com.candido.server.domain.v1.user.User_;
import com.candido.server.dto.v1.request.account.RequestUpdateUser;
import com.candido.server.dto.v1.request.geo.RequestAddress;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionAccountNotFound;
import com.candido.server.service.base.geo.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final GenderService genderService;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            AddressService addressService,
            GenderService genderService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.genderService = genderService;
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
    public User save(User user, RequestUpdateUser requestUpdateUser, boolean canChangeName) {
        if(canChangeName) {
            user.setFirstName(requestUpdateUser.firstName());
            user.setLastName(requestUpdateUser.lastName());
            user.setLastModifiedName(LocalDateTime.now());
        }

        Gender gender = genderService.findGenderByIdOrThrow(requestUpdateUser.genderId());
        user.setGenderId(gender.getId());
        user.setBirthdate(requestUpdateUser.birthdate());
        user.setMobileNumber(requestUpdateUser.mobileNumber());
        user.setPhoneNumber(requestUpdateUser.phoneNumber());

        if (requestUpdateUser.address() != null) {
            Address address = addressService.saveAddress(
                    user.getAddressId(), requestUpdateUser.address()
            );
            user.setAddressId(address.getAddressId());
        }

        save(user);
        return findUserByAccountIdOrThrow(user.getAccountId());
    }

    @Override
    public User updateAddress(User user, RequestAddress requestUserAddressDto) {
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
