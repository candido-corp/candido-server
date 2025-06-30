package com.candido.server.service.base.geo.specifications;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.geo.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecifications {

    public static Specification<Address> byId(Integer addressId) {
        return (root, query, cb) -> cb.equal(root.get(Address_.ADDRESS_ID), addressId);
    }

    public static Specification<Address> byUserId(Long userId) {
        return (root, query, cb) -> cb.equal(root.get(Address_.USER_ID), userId);
    }

    public static Specification<Address> isPrimary() {
        return (root, query, cb) -> cb.isTrue(root.get(Address_.IS_PRIMARY));
    }

    public static Specification<Address> isActive() {
        return (root, query, cb) -> cb.isNull(root.get(Address_.DELETED_AT));
    }

    public static Specification<Address> byIdAndUserId(Integer addressId, Long userId) {
        return Specification.where(byId(addressId)).and(byUserId(userId));
    }

    public static Specification<Address> byUserIdAndIsPrimary(Long userId) {
        return Specification.where(byUserId(userId)).and(isPrimary());
    }
}
