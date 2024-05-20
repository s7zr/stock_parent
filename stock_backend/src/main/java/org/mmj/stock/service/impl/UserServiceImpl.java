package org.mmj.stock.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.mmj.stock.mapper.SysUserMapper;
import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.service.UserService;
import org.mmj.stock.vo.req.LoginReqVo;
import org.mmj.stock.vo.resp.LoginRespVo;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    @Override
    public SysUser getUserByUserName(String userName) {
        return sysUserMapper.getUserByUserName(userName);
    }

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        //1.判断参数是否合法
        if (vo==null || StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword())){
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //根据用户名查询用户信息
        SysUser user=this.sysUserMapper.getUserByUserName(vo.getUsername());
        //判断用户是否存在，存在则密码校验比对
        if (user==null || !passwordEncoder.matches(vo.getPassword(),user.getPassword())){
            return R.error(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());
        }
        //组装登录成功数据
        LoginRespVo respVo = new LoginRespVo();
        //属性名称与类型必须相同，否则属性值无法copy
        BeanUtils.copyProperties(user,respVo);
        return  R.ok(respVo);
    }
}
