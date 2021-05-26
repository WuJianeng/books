package org.jianeng.books.controller;

import lombok.extern.slf4j.Slf4j;
import org.jianeng.books.model.UserAddress;
import org.jianeng.books.service.UserAddressService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/25 10:31
 */
@SpringBootTest
@Slf4j
public class UserAddressControllerTest {
    @Autowired
    private UserAddressController userAddressController;

    @Autowired
    private UserAddressService userAddressService;

    private MockMvc mockMvc;

    private Logger logger = LoggerFactory.getLogger(UserAddressControllerTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * 测试获取用户所有地址功能
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback
    public void testGetAddress() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/address/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isArray())
                .andExpect(jsonPath("$.address", hasItem("北京市海淀区")))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 测试用户地址更新功能， 注意考虑了输入地址前后包含空白的情况
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback
    public void testUpdateAddress() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/address/update").param("address_id", "1")
                                                                                .param("address", " 北京市西二旗 "))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        UserAddress userAddress = userAddressService.getUserAddressByAddressId(1L);
        Assert.assertEquals("北京市西二旗", userAddress.getAddress());
    }

    @Test
    @Transactional
    @Rollback
    public void testAddAddress() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/address/add/1").param("address", " 花果山水帘洞 "))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        List<UserAddress> userAddressList = userAddressService.getUserAddressListByUserId(1L);
        Assert.assertTrue(userAddressList.size() > 0);

        userAddressList.forEach(address -> logger.info(address.toString()));

        for (UserAddress address : userAddressList) {
            if (address.getAddress().equals("花果山水帘洞")) {
                Assert.assertTrue(true);
                return ;
            }
        }
        Assert.assertTrue(false);
    }
}
