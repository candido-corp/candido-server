package com.candido.server.service.user;

import com.candido.server.domain.v1.user.User;
import com.candido.server.domain.v1.user.UserRepository;
import com.candido.server.domain.v1.user.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> findByAccountId(int accountId) {
        Specification<User> byAccountId = ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(User_.ACCOUNT_ID), accountId));
        return userRepository.findOne(byAccountId);
    }
}
