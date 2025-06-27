package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddressByUser(Long userId);
    Address getAddressById(Long id, Long userId);
    boolean addAddress(Address address);
    boolean updateAddress(Address address);
    boolean deleteAddress(Address address);
}
