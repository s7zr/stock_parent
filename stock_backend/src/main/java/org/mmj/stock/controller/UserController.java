package org.mmj.stock.controller;

import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.service.UserService;
import org.mmj.stock.vo.req.LoginReqVo;
import org.mmj.stock.vo.resp.LoginRespVo;
import org.mmj.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    @GetMapping("/user/{userName}")
    public SysUser getUserByUserName(@PathVariable("userName") String userName){
        return userService.getUserByUserName(userName);
    }
    /**
     * 用户登录功能实现
     * @param vo
     * @return
     */
    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo){
        R<LoginRespVo> r= this.userService.login(vo);
        return r;
    }
}
