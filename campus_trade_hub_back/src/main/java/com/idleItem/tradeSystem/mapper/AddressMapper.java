package com.idleItem.tradeSystem.mapper;

import com.idleItem.tradeSystem.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    int deleteByPrimaryKeyAndUser(Long id,Long userId);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long id);

    List<Address> getAddressByUser(Long userId);

    List<Address> getDefaultAddress(Long userId);

    int updateByPrimaryKeySelective(Address record);

    void updateByUserIdSelective(Address record);

    int updateByPrimaryKey(Address record);
}