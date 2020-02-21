package com.wuyan.bms.controller;

import com.wuyan.bms.common.dto.RoleDto;
import com.wuyan.bms.common.result.PageTableRequest;
import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.model.SysRole;
import com.wuyan.bms.model.SysUser;
import com.wuyan.bms.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    @ResponseBody
    public Results<SysRole> getAll() {
        log.info("RoleController.getAll()");
        return roleService.getAllRoles();
    }

    @GetMapping("/list")
    @ResponseBody
    public Results<SysRole> getUsers(PageTableRequest request) {
        log.info("UserController.getUsers(): param ( request1 = " + request +" )");
        request.countOffset();
        return roleService.getAllRolesByPage(request.getOffset(), request.getLimit());
    }

    @GetMapping("/findRoleByFuzzyRoleName")
    @ResponseBody
    public Results<SysRole> findRoleByFuzzyRoleName(PageTableRequest request, String name) {
        log.info("UserController.findUserByFuzzyUsername(): param ( request1 = " + request +" ,RoleName = " + name+ ")");
        request.countOffset();
        return roleService.findRoleByFuzzyRoleName(name, request.getOffset(), request.getLimit());
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("sysRole",new SysRole());
        return "role/role-add";
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public Results saveRole(@RequestBody RoleDto role) {
        return roleService.save(role);
    }

    @GetMapping("/edit")
    public String editRole(Model model, SysRole role) {
        model.addAttribute("sysRole",roleService.getRoleById(role.getId()));
        return "role/role-edit";
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public Results updateRole(@RequestBody RoleDto role) {
        return roleService.update(role);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Results deleteRole(Integer id) {
        return roleService.deleteRole(id);
    }
}
