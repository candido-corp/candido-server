package com.candido.server.service.business.user;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUser;
import com.candido.server.dto.v1.util.UserDto;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.application.ApplicationService;
import com.candido.server.service.base.geo.AddressService;
import com.candido.server.service.base.geo.specifications.AddressSpecifications;
import com.candido.server.service.base.mapper.UserMapperService;
import com.candido.server.service.base.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class BusinessUserServiceImpl implements BusinessUserService {

    private final UserService userService;
    private final AccountService accountService;
    private final UserMapperService userMapper;
    private final ApplicationService applicationService;
    private final AddressService addressService;


    public BusinessUserServiceImpl(
            AccountService accountService,
            UserMapperService userMapper,
            UserService userService,
            ApplicationService applicationService,
            AddressService addressService
    ) {
        this.accountService = accountService;
        this.userMapper = userMapper;
        this.userService = userService;
        this.applicationService = applicationService;
        this.addressService = addressService;
    }

    @Override
    public UserDto getUserDtoWithPrimaryAddress(Authentication authentication) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        boolean userHasOpenApplications = applicationService.userHasOpenApplications(account.getId());

        Address primaryAddress = addressService.findActiveAddressBySpecification(
                AddressSpecifications.byUserIdAndIsPrimary(user.getId())
        ).orElse(null);

        return userMapper.userToUserDto(user, account, userHasOpenApplications, primaryAddress);
    }

    @Override
    public UserDto editUserInfo(Authentication authentication, RequestUpdateUser requestUpdateUser) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        boolean userHasOpenApplications = applicationService.userHasOpenApplications(account.getId());
        boolean canChangeName = !userHasOpenApplications;
        User user = userService.findUserByAccountIdOrThrow(account.getId());
        User currentUser = userService.save(user, requestUpdateUser, canChangeName);

        Address primaryAddress = addressService.findActiveAddressBySpecification(
                AddressSpecifications.byUserIdAndIsPrimary(user.getId())
        ).orElse(null);

        return userMapper.userToUserDto(
                currentUser,
                account,
                userHasOpenApplications,
                primaryAddress
        );
    }
}
