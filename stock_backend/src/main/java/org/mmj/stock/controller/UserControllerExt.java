package org.mmj.stock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mmj.stock.service.UserServiceExt;
import org.mmj.stock.vo.req.*;
import org.mmj.stock.vo.resp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-06 20:38
 */
@Api(value = "/api", tags = {"股票接口"})
@RestController
@RequestMapping("/api")
public class UserControllerExt {

    /**
     * 注入服务
     */
    @Autowired
    private UserServiceExt userService;


    /**
     * 多条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @param userPageReqVo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserPageReqVo", name = "userPageReqVo", value = "", required = true)
    })
    @ApiOperation(value = "多条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围", notes = "多条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围", httpMethod = "POST")
    @PostMapping("/users")
    public R<PageResult>  pageUsers(@RequestBody UserPageReqVo userPageReqVo){
        return this.userService.pageUsers(userPageReqVo);
    }

    /**
     * 添加用户信息
     * @param vo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserAddReqVo", name = "vo", value = "", required = true)
    })
    @ApiOperation(value = "添加用户信息", notes = "添加用户信息", httpMethod = "POST")
    @PostMapping("/user")
    public R<String> addUser(@RequestBody UserAddReqVo vo){
        return this.userService.addUser(vo);
    }

    /**
     * 更新用户信息
     * @param vo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserEditReqVO", name = "vo", value = "", required = true)
    })
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "PUT")
    @PutMapping("/user")
    public R<String> updateUser(@RequestBody UserEditReqVO vo){
        return this.userService.updateUser(vo);
    }

    /**
     * 获取用户具有的角色信息，以及所有角色信息
     * @param userId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "userId", value = "", required = true)
    })
    @ApiOperation(value = "获取用户具有的角色信息，以及所有角色信息", notes = "获取用户具有的角色信息，以及所有角色信息", httpMethod = "GET")
    @GetMapping("/user/roles/{userId}")
    public R<UserOwnRoleRespVo> getUserOwnRole(@PathVariable("userId")Long userId){
        return this.userService.getUserOwnRole(userId);
    }

    /**
     * 更新用户角色信息
     * @param vo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserOwnRoleReqVo", name = "vo", value = "", required = true)
    })
    @ApiOperation(value = "更新用户角色信息", notes = "更新用户角色信息", httpMethod = "PUT")
    @PutMapping("/user/roles")
    public R<String> updateUserOwnRoles(@RequestBody UserOwnRoleReqVo vo){
        return this.userService.updateUserOwnRoles(vo);
    }

    /**
     * 批量删除用户信息
     * delete请求可通过请求体携带数据
     * @param userIds
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<Long>", name = "userIds", value = "", required = true)
    })
    @ApiOperation(value = "批量删除用户信息 delete请求可通过请求体携带数据", notes = "批量删除用户信息 delete请求可通过请求体携带数据", httpMethod = "DELETE")
    @DeleteMapping("/user")
    public R<String> deleteUsers(@RequestBody List<Long> userIds){
        return this.userService.deleteUsers(userIds);
    }


    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "userId", value = "", required = true)
    })
    @ApiOperation(value = "根据用户id查询用户信息", notes = "根据用户id查询用户信息", httpMethod = "GET")
    @GetMapping("/user/info/{userId}")
    public R<UserInfoRespVo> getUserInfo(@PathVariable("userId") Long id){
        return userService.getUserInfo(id);
    }


}
