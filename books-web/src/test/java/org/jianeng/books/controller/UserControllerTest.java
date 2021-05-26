package org.jianeng.books.controller;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/25 10:33
 */
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    @Transactional
    @Rollback
    public void testQueryUserById() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.gender").exists())
                .andExpect(jsonPath("$.age").exists())
                .andExpect(jsonPath("$.email").exists())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Transactional
    @Rollback
    public void testRegisterUser() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                .param("name", "testName123456")
                .param("email", "testName1@gmail.com"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
