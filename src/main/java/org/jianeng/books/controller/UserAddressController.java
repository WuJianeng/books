package org.jianeng.books.controller;

import org.jianeng.books.model.UserAddress;
import org.jianeng.books.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 23:19
 */
@RestController
@RequestMapping("/address")
@CrossOrigin
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/all/{user_id}")
    public ResponseEntity<List<UserAddress>> getAllUserAddressByUserId(@PathVariable("user_id")Integer userId) {
        List<UserAddress> userAddressList = userAddressService.getUserAddressListByUserId(userId);
        return ResponseEntity.ok(userAddressList);
    }

    /**
     * 基于 userAddress 进行模糊查询所有符合要求的 UserAddress
     * @param userAddress
     * @return
     */
    @PostMapping("/all")
    public ResponseEntity<List<UserAddress>> getAddressListByAddress(@RequestBody UserAddress userAddress) {
        List<UserAddress> userAddressList = userAddressService.getUserAddressListByUserAddress(userAddress);
        return ResponseEntity.ok(userAddressList);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Boolean> addUserAddress(@RequestBody UserAddress address) {
        boolean res = userAddressService.addUserAddress(address.getUserId(), address.getAddress());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateAddress(@RequestBody UserAddress address) {
        boolean res = userAddressService.updateUserAddress(address.getId(), address.getAddress());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/delete/{address_id}")
    public ResponseEntity<Boolean> deleteAddress(@PathVariable("address_id") Integer addressId) {
        Boolean res = userAddressService.deleteUserAddress(addressId);
        return ResponseEntity.ok(res);
    }
}
