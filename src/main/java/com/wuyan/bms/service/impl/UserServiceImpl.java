package com.wuyan.bms.service.impl;

import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.mapper.RoleUserDao;
import com.wuyan.bms.mapper.UserDao;
import com.wuyan.bms.model.SysRoleUser;
import com.wuyan.bms.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.wuyan.bms.model.SysUser;
/**
 *  SysUserServiceImpl
 *  @author {yuanwei}
 *  @date 2020/2/11 22:51
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Resource
    private RoleUserDao roleUserDao;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Results<SysUser> getAllUsersByPage(Integer offset, Integer limit) {
        return Results.success(userDao.countAllUsers().intValue(), userDao.getAllUsersByPage(offset, limit));
    }


    @Override
    public SysUser getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public Boolean checkPhoneUnique(SysUser user) {
        Long userId = user.getId()==null ? -1L : user.getId();
        SysUser info = userDao.getUserByTelephone(user.getTelephone());
        if (info!=null && info.getId().longValue() != userId.longValue()) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Results<SysUser> updateUser(SysUser sysUser, Integer roleId) {
        if(roleId != null){
            //sysuser
            userDao.updateUser(sysUser);
            //sysroleuser update save
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(sysUser.getId().intValue());
            sysRoleUser.setRoleId(roleId);
            if(roleUserDao.getSysRoleUserByUserId(sysUser.getId().intValue()) != null){
                roleUserDao.updateSysRoleUser(sysRoleUser);
            }else{
                roleUserDao.save(sysRoleUser);
            }
            return Results.success();
        }else{
            return Results.failure();
        }
    }

    @Override
    public int deleteUser(Long id) {
        //sysroleuser
        roleUserDao.deleteRoleUserByUserId(id.intValue());
        //sysuser
        return userDao.deleteUser(id.intValue());
    }

    @Override
    public Results<SysUser> getUserByFuzzyUsername(String username, Integer offset, Integer limit) {
        return Results.success(userDao.getUserByFuzzyUsername(username).intValue(),
                userDao.getUserByFuzzyUsernameByPage(username,offset,limit));
    }

    @Override
    public int deleteBatch(Integer[] id) {

        //sysroleuser
        roleUserDao.deleteBatchRoleUserByUserId(id);
        //sysuser
        return userDao.deleteBatchUser(id);
    }

    @Override
    public SysUser getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public Results<SysUser> save(SysUser user, Integer roleId) {
        if(roleId != null){
            //user
            userDao.save(user);
            //roleUser
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setUserId(user.getId().intValue());
            roleUserDao.save(sysRoleUser);
            return Results.success();
        }
        return Results.failure();
    }

    @Override
    public Results<SysUser> changePassword(Long id, String oldPassword, String newPassword) {
        SysUser u = userDao.getUserById(id);
        if (u == null) {
            return Results.failure(1,"用户不存在");
        }
        if (!passwordEncoder.matches(oldPassword,u.getPassword())) {
            return Results.failure(1,"旧密码错误");
        }
        userDao.changePassword(u.getId(), passwordEncoder.encode(newPassword));
        return Results.success();
    }

}
