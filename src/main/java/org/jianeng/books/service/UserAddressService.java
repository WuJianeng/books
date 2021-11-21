package org.jianeng.books.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableMap;
import org.jianeng.books.mapper.UserAddressMapper;
import org.jianeng.books.model.UserAddress;
import org.jianeng.books.model.exception.RequestValidationFailedException;
import org.jianeng.books.model.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 23:17
 */

@Service
public class UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    private static Logger logger = LoggerFactory.getLogger(UserAddressService.class);

    /**
     * 获取用户的所有地址
     * @param userId
     * @return
     */
    public List<UserAddress> getUserAddressListByUserId(Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        List<UserAddress> userAddressList = userAddressMapper.selectList(queryWrapper);
        return userAddressList;
    }

    /**
     * 基于 userAddress 进行模糊查询所有符合要求的 UserAddress
     */
    public List<UserAddress> getUserAddressListByUserAddress(UserAddress userAddress) {
        String address = userAddress.getAddress();
        userAddress.setAddress(null);
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>(userAddress);
        if (address != null && !address.trim().equals("")){
            wrapper.like("address", address);
        }
        List<UserAddress> userAddressList = userAddressMapper.selectList(wrapper);
        return userAddressList;
    }

    /**
     * 根据地址 id 修改地址信息
     * @param addressId
     * @return
     */
    public UserAddress getUserAddressByAddressId(Integer addressId) {
        UserAddress address = userAddressMapper.selectById(addressId);
        if (address == null) {
            throw new ResourceNotFoundException(ImmutableMap.of("addressId:", addressId));
        }
        return address;
    }

    /**
     * 新增用户地址
     * @param userId
     * @param address
     * @return 已存在该地址时返回 false, 插入成功返回 true
     */
    public boolean addUserAddress(Integer userId, String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new RequestValidationFailedException(ImmutableMap.of("address", address));
        } else {
            address = address.trim();
        }
        List<UserAddress> userAddressList = getUserAddressListByUserId(userId);
        for (UserAddress userAddress : userAddressList) {
            if (address.equals(userAddress.getAddress())) {
                logger.info("userId: " + userId + " address： " + address + " 试图重复添加地址.");
                return false;
            }
        }

        UserAddress userAddress = new UserAddress();
        userAddress.setId(null);
        userAddress.setAddress(address);
        userAddress.setUserId(userId);

        userAddressMapper.insert(userAddress);
        return true;
    }

    /**
     * 修改用户地址
     * @param addressId
     * @param address
     * @return
     */
    public boolean updateUserAddress(Integer addressId, String address) {
        if (addressId == null || address == null || address.trim().isEmpty()) {
            throw new RequestValidationFailedException(ImmutableMap.of("address_id:", addressId, "address:", address));
        }

        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setAddress(address.trim());
        userAddressMapper.updateById(userAddress);

        return true;
    }

    /**
     * 根据 id 删除指定的用户地址
     * @param id
     * @return
     */
    public boolean deleteUserAddress(Integer id) {
        int res = userAddressMapper.deleteById(id);
        if (res <= 0) {
            throw  new RequestValidationFailedException(ImmutableMap.of("id is illegal.", id));
        } else {
            return true;
        }
    }
}
