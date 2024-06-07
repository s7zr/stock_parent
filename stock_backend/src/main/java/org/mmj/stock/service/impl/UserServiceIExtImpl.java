package org.mmj.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.mmj.stock.exception.BusinessException;
import org.mmj.stock.mapper.*;
import org.mmj.stock.pojo.domain.*;
import org.mmj.stock.pojo.entity.SysRole;
import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.pojo.entity.SysUserRole;
import org.mmj.stock.service.UserServiceExt;
import org.mmj.stock.utils.IdWorker;
import org.mmj.stock.vo.req.UserAddReqVo;
import org.mmj.stock.vo.req.UserEditReqVO;
import org.mmj.stock.vo.req.UserOwnRoleReqVo;
import org.mmj.stock.vo.req.UserPageReqVo;
import org.mmj.stock.vo.resp.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("userServiceExt")
@Slf4j
public class UserServiceIExtImpl implements UserServiceExt {
    @Autowired
    private SysUserMapperExt sysUserMapperExt;
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 多条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @param vo
     * @return
     */
    @Override
    public R<PageResult> pageUsers(UserPageReqVo vo) {
        //组装分页数据
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        //设置查询条件
        List<UserQueryDomain> users= this.sysUserMapperExt.pageUsers(vo.getUsername(),vo.getNickName(),vo.getStartTime(),vo.getEndTime());
        if (CollectionUtils.isEmpty(users)) {
            return R.error("没有数据");
        }
        PageResult<UserQueryDomain> pageResult = new PageResult<>(new PageInfo<>(users));
        return R.ok(pageResult);
    }

    /**
     * 添加用户信息1
     * @param vo
     * @return
     */
    @Override
    public R<String> addUser(UserAddReqVo vo) {
        //1.判断当前账户username是否已被使用
        SysUser dbUser= this.sysUserMapperExt.findUserByUserName(vo.getUsername());
        if (dbUser!=null) {
            //抛出业务异常 等待全局异常处理器统一处理
            throw new BusinessException(ResponseCode.ACCOUNT_EXISTS_ERROR.getMessage());
        }
        //2.否则添加
        //封装用户信息
        SysUser user = new SysUser();
        BeanUtils.copyProperties(vo,user);
        //设置用户id
        user.setId(idWorker.nextId());
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //设置添加时间和更新时间
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        //是否删除
        user.setDeleted(1);
        //TODO 获取当前操作用户的id
        int count = this.sysUserMapperExt.insert(user);
        if (count!=1) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }
    /**
     * 更新用户信息
     * @param vo
     * @return
     */
    @Override
    public R<String> updateUser(UserEditReqVO vo) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(vo,user);
        //TODO 设置更新者ID
        //设置更新时间
        user.setUpdateTime(new Date());
        //更新用户信息
        int count = this.sysUserMapperExt.updateByPrimaryKeySelective(user);
        if (count!=1) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }
    /**
     * 获取用户具有的角色信息，以及所有角色信息
     * @param userId
     * @return
     */
    @Override
    public R<UserOwnRoleRespVo> getUserOwnRole(Long userId) {
        //1.获取当前用户所拥有的角色id集合
        List<Long> roleIds= this.sysUserRoleMapper.findRoleIdsByUserId(userId);
        //2.获取所有角色信息
        List<SysRole> roles= this.sysRoleMapper.findAll();
        //3.封装数据
        UserOwnRoleRespVo vo = new UserOwnRoleRespVo();
        vo.setOwnRoleIds(roleIds);
        vo.setAllRole(roles);
        return R.ok(vo);
    }
    /**
     * 更新用户角色信息
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateUserOwnRoles(UserOwnRoleReqVo vo) {
        //1.判断用户id是否存在
        if (vo.getUserId()==null) {
            throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
        }
        //2.删除用户原来所拥有的角色id
        this.sysUserRoleMapper.deleteByUserId(vo.getUserId());
        //如果对应集合为空，则说明用户将所有角色都清除了
        if (CollectionUtils.isEmpty(vo.getRoleIds())) {
            return R.ok(ResponseCode.SUCCESS.getMessage());
        }
        //封装用户角色对象集合

        List<SysUserRole> list = vo.getRoleIds().stream().map(roleId -> {
            SysUserRole userRole = SysUserRole.builder().
                    userId(vo.getUserId()).roleId(roleId).
                    createTime(new Date()).id(idWorker.nextId()).build();
            return userRole;
        }).collect(Collectors.toList());
        //批量插入
        int count= this.sysUserRoleMapper.insertBatch(list);
        if (count==0) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }
    /**
     * 批量删除用户信息
     * @param userIds
     * @return
     */
    @Override
    public R<String> deleteUsers(List<Long> userIds) {
        //判断集合是否为空
        if (CollectionUtils.isEmpty(userIds)) {
            throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
        }
        //删除用户未逻辑删除
        int result=this.sysUserMapperExt.updateUserStatus4Deleted(userIds);
        if (result==0) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }
    /**
     * 根据用户ID查询用户细腻
     * @param id 用户id
     * @return
     */
    @Override
    public R<UserInfoRespVo> getUserInfo(Long id) {
        if (id==null) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        SysUser user = this.sysUserMapperExt.selectByPrimaryKey(id);
        UserInfoRespVo userInfo = new UserInfoRespVo();
        BeanUtils.copyProperties(user,userInfo);
        return R.ok(userInfo);
    }
}
