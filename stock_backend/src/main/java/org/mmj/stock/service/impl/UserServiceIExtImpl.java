package org.mmj.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.mmj.stock.exception.BusinessException;
import org.mmj.stock.mapper.*;
import org.mmj.stock.pojo.domain.*;
import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.service.UserServiceExt;
import org.mmj.stock.utils.IdWorker;
import org.mmj.stock.vo.req.UserAddReqVo;
import org.mmj.stock.vo.req.UserEditReqVO;
import org.mmj.stock.vo.req.UserPageReqVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service("userServiceExt")
@Slf4j
public class UserServiceIExtImpl implements UserServiceExt {
    @Autowired
    private SysUserMapperExt sysUserMapperExt;
    @Autowired
    private IdWorker idWorker;

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
}
