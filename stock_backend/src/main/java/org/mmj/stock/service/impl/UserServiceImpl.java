package org.mmj.stock.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mmj.stock.constant.StockConstant;
import org.mmj.stock.mapper.SysUserMapper;
import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.service.UserService;
import org.mmj.stock.utils.IdWorker;
import org.mmj.stock.vo.req.LoginReqVo;
import org.mmj.stock.vo.resp.LoginRespVo;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;
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
        //判断校验码是否输入,同时检查sessionId
        if (StringUtils.isBlank(vo.getCode()) || StringUtils.isBlank(vo.getSessionId()) ){
            return R.error(ResponseCode.CHECK_CODE_NOT_EMPTY);
        }
        //判断校验码是否一致,忽略大小写
        String redisCheckCode = (String) redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + vo.getSessionId());
        if (!vo.getCode().equalsIgnoreCase(redisCheckCode)) {
            return R.error(ResponseCode.CHECK_CODE_ERROR);
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

    @Override
    public R<Map> getCaptchaCode() {
        //参数分别是宽、高、验证码长度、干扰线数量
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        //设置背景颜色清灰
        captcha.setBackground(Color.lightGray);
        //获取图片中的验证码，默认生成的校验码包含文字和数字，长度为4
        String checkCode = captcha.getCode();
        log.info("生成校验码:{}",checkCode);
        //生成sessionId,因为sessionId会传递给前端，因此使用String，避免精度丢失
        String sessionId = String.valueOf(idWorker.nextId());
        //将sessionId和校验码保存在redis下，并设置缓存中数据存活时间一分钟
        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX +sessionId,checkCode,1, TimeUnit.MINUTES);
        //组装响应数据
        HashMap<String, String> info = new HashMap<>();
        info.put("sessionId",sessionId);
        info.put("imageData",captcha.getImageBase64());//获取base64格式的图片数据
        //设置响应数据格式
        return R.ok(info);
    }
}
