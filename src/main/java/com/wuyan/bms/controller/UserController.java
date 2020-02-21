package com.wuyan.bms.controller;

import com.wuyan.bms.common.result.PageTableRequest;
import com.wuyan.bms.common.result.ResponseCode;
import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.common.utils.MD5;
import com.wuyan.bms.model.SysUser;
import com.wuyan.bms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UserController
 *
 * @author {yuanwei}
 * @date 2020/2/11 22:32
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    public Results<SysUser> getUsers(PageTableRequest request) {
        log.info("UserController.getUsers(): param ( request1 = " + request +" )");
        request.countOffset();
        return userService.getAllUsersByPage(request.getOffset(), request.getLimit());
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add1')")
    public String addUser(Model model) {
        model.addAttribute(new SysUser());
        return "user/user-add";
    }

    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:add1')")
    public Results<SysUser> addUser(SysUser user,int roleId) {
        if (userService.checkPhoneUnique(user)){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(), ResponseCode.PHONE_REPEAT.getMessage());
        }
        user.setStatus(1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user, roleId);
    }

    @GetMapping("/edit")
    public String editUser(Model model, SysUser sysUser) {
        model.addAttribute(userService.getUserById(sysUser.getId()));
        return "user/user-edit";
    }


    @PostMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public Results<SysUser> updateUser(SysUser sysUser, Integer roleId) {

        if(userService.checkPhoneUnique(sysUser)){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(), ResponseCode.PHONE_REPEAT.getMessage());
        }

        return userService.updateUser(sysUser, roleId);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    public Results deleteUser(SysUser user) {
        int count = userService.deleteUser(user.getId());
        if(count > 0){
            return Results.success();
        }else{
            return Results.failure();
        }
    }

    @GetMapping("/findUserByFuzzyUsername")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    public Results<SysUser> findUserByFuzzyUsername(PageTableRequest request, String username) {
        log.info("UserController.findUserByFuzzyUsername(): param ( request1 = " + request +" ,username = " + username+ ")");
        request.countOffset();
        return userService.getUserByFuzzyUsername(username, request.getOffset(), request.getLimit());
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    public Results<SysUser> deleteBatch(Integer[] id) {

        int count = userService.deleteBatch(id);
        if(count > 0){
            return Results.success();
        }else{
            return Results.failure();
        }
    }


    @PostMapping("/changePassword")
    @ResponseBody
    public Results<SysUser> changePassword(Long id, String oldPassword, String newPassword) {

        return userService.changePassword(id, oldPassword, newPassword);
    }

}
