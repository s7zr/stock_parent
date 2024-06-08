package org.mmj.stock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.service.UserService;
import org.mmj.stock.vo.req.LoginReqVo;
import org.mmj.stock.vo.resp.LoginRespVo;
import org.mmj.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * 定义访问用户的服务接口
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户操作接口")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    @GetMapping("/user/{userName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value = "用户名",required = true,dataType = "String",paramType = "path")
    })
    public SysUser getUserByUserName(@PathVariable("userName") String userName){
        return userService.getUserByUserName(userName);
    }
    /**
     * 用户登录功能实现
     * @param vo
     * @return
     */
//    @ApiOperation("用户登录功能接口")
//    @PostMapping("/login")
//    public R<LoginRespVo> login(@RequestBody LoginReqVo vo){
//        R<LoginRespVo> r= this.userService.login(vo);
//        return r;
//    }

    /**
     * 生成登录校验码的访问接口
     * @return
     */
    @ApiOperation("获取验证码接口")
    @GetMapping("/captcha")
    public R<Map> getCaptchaCode(){
        return userService.getCaptchaCode();
    }
}
