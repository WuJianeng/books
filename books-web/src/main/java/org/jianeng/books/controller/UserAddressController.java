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
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/{user_id}")
    public ResponseEntity<List<UserAddress>> getAllUserAddressByUserId(@PathVariable("user_id")Long userId) {
        List<UserAddress> userAddressList = userAddressService.getUserAddressListByUserId(userId);
        return ResponseEntity.ok(userAddressList);
    }

    @PostMapping(value = "/add/{user_id}")
    public ResponseEntity<Boolean> addUserAddress(@PathVariable("user_id")Long userId, @RequestParam("address")String address) {
        boolean res = userAddressService.addUserAddress(userId, address);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateAddress(@RequestParam("address_id")Long addressId, @RequestParam("address")String address) {
        boolean res = userAddressService.updateUserAddress(addressId, address);
        return ResponseEntity.ok(res);
    }
}
