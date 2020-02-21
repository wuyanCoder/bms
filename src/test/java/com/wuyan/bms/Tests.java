package com.wuyan.bms;

import com.wuyan.bms.controller.UserController;
import com.wuyan.bms.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests
 *
 * @author {yuanwei}
 * @date 2020/2/16 22:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BmsApplication.class})
@Slf4j
public class Tests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserController userController;

    @Test
    public void encoder(){
        log.info(passwordEncoder.encode("123456"));
    }

    @Test
    public void saveUser(){
        SysUser user = new SysUser();
        user.setPassword("123456");
        user.setUsername("yuanw");
        userController.addUser(user,1);
    }


    @Test
    public void updatePassword(){
        userController.changePassword(47L,"123456","123456");
    }


}
