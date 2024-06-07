package org.mmj.stock.controller;

import org.mmj.stock.pojo.entity.SysPermission;
import org.mmj.stock.service.PermissionService;
import org.mmj.stock.vo.req.PermissionAddVo;
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
@RestController
@RequestMapping("/api")
public class PermissionContrlller {
    @Autowired
    private PermissionService permissionService;
    /**
     * 权限数据显示（仅仅包含id title childen）
     * @return
     */
    @GetMapping("/permissions/tree/all")
    public R<List<PermissionRespNodeVo>> getAllPermissionTree(){
        return permissionService.selectAllTree();
    }
    /**
     * 查询所有权限集合
     * @return
     */
    @GetMapping("/permissions")
    public R<List<SysPermission>>  getAll(){
        return permissionService.getAll();
    }
    /**
     * 添加权限时回显权限树
     * @return
     */
    @GetMapping("/permissions/tree")
    public R<List<PermissionRespNodeTreeVo>>  getAllPermissionTreeExBt(){
        return permissionService.getAllPermissionTreeExBt();
    }

    /**
     * 权限添加按钮
     * @param vo
     * @return
     */
    @PostMapping("/permission")
    public R<String> addPermission(@RequestBody PermissionAddVo vo){
        return permissionService.addPermission(vo);
    }
}
