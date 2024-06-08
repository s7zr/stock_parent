package org.mmj.stock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mmj.stock.pojo.entity.SysPermission;
import org.mmj.stock.service.PermissionService;
import org.mmj.stock.vo.req.PermissionAddVo;
import org.mmj.stock.vo.req.PermissionUpdateVo;
import org.mmj.stock.vo.resp.PermissionRespNodeTreeVo;
import org.mmj.stock.vo.resp.PermissionRespNodeVo;
import org.mmj.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 15:33
 */
@Api(tags = "权限接口")
@RestController
@RequestMapping("/api")
public class PermissionContrlller {
    @Autowired
    private PermissionService permissionService;
    /**
     * 权限数据显示（仅仅包含id title childen）
     * @return
     */
    @ApiOperation(value = "权限数据显示（仅仅包含id title childen）", notes = "权限数据显示（仅仅包含id title childen）", httpMethod = "GET")
    @GetMapping("/permissions/tree/all")
    public R<List<PermissionRespNodeVo>> getAllPermissionTree(){
        return permissionService.selectAllTree();
    }
    /**
     * 查询所有权限集合
     * @return
     */
    @ApiOperation(value = "查询所有权限集合", notes = "查询所有权限集合", httpMethod = "GET")
    @GetMapping("/permissions")
    public R<List<SysPermission>>  getAll(){
        return permissionService.getAll();
    }
    /**
     * 添加权限时回显权限树
     * @return
     */
    @ApiOperation(value = "添加权限时回显权限树", notes = "添加权限时回显权限树", httpMethod = "GET")
    @GetMapping("/permissions/tree")
    public R<List<PermissionRespNodeTreeVo>>  getAllPermissionTreeExBt(){
        return permissionService.getAllPermissionTreeExBt();
    }

    /**
     * 权限添加按钮
     * @param vo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PermissionAddVo", name = "vo", value = "", required = true)
    })
    @ApiOperation(value = "权限添加按钮", notes = "权限添加按钮", httpMethod = "POST")
    @PostMapping("/permission")
    public R<String> addPermission(@RequestBody PermissionAddVo vo){
        return permissionService.addPermission(vo);
    }

    /**
     * 更新权限
     * @param vo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PermissionUpdateVo", name = "vo", value = "", required = true)
    })
    @ApiOperation(value = "更新权限", notes = "更新权限", httpMethod = "PUT")
    @PutMapping("/permission")
    public R<String> updatePermission(@RequestBody PermissionUpdateVo vo){
        return permissionService.updatePermission(vo);
    }

    /**
     * 删除权限
     * @param permissionId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "permissionId", value = "", required = true)
    })
    @ApiOperation(value = "删除权限", notes = "删除权限", httpMethod = "DELETE")
    @DeleteMapping("/permission/{permissionId}")
    public R<String> removePermission(@PathVariable("permissionId") Long permissionId){
        return permissionService.removePermission(permissionId);
    }
}
