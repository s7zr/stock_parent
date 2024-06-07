package org.mmj.stock.controller;

import org.mmj.stock.service.RoleService;
import org.mmj.stock.vo.req.RoleAddVo;
import org.mmj.stock.vo.req.RoleUpdateVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.RolePageReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 14:53
 */
@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleService roleService;
    /**
     * 分页查询当前角色信息
     * @param vo
     * @return
     */
    @PostMapping("/roles")
    public R<PageResult> queryPageRole(@RequestBody RolePageReqVo vo){
        return roleService.queryPageRole(vo);
    }
    /**
     * 添加角色和角色关联权限
     * @param vo
     * @return
     */
    @PostMapping("/role")
    public R<String> addRoleWithPermissions(@RequestBody RoleAddVo vo){
        return roleService.addRoleWithPermissions(vo);
    }

    /**
     * 根据角色id查找对应的权限id集合
     * @param roleId
     * @return
     */
    @GetMapping("/role/{roleId}")
    public R<Set<String>>  getPermissionIdsByRoleId(@PathVariable("roleId") Long roleId){
        return roleService.getPermissionIdsByRoleId(roleId);
    }

    /**
     * 更新角色信息，包含角色关联的权限信息
     * @param vo
     * @return
     */
    @PutMapping("/role")
    public R<String> updateRoleWithPermissions(@RequestBody RoleUpdateVo vo){
        return roleService.updateRoleWithPermissions(vo);
    }
}
