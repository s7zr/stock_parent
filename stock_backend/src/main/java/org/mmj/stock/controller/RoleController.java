package org.mmj.stock.controller;

import io.swagger.annotations.Api;
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
@Api(tags = "角色接口")
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
    /**
     * 根据角色id删除角色信息
     * @param roleId
     * @return
     */
    @DeleteMapping("/role/{roleId}")
    public R<String> deleteRole(@PathVariable("roleId")Long roleId){
        return roleService.deleteRoleById(roleId);
    }

    /**
     * 更新用户的状态信息
     * @param roleId 角色id
     * @param status 状态 1.正常 0：禁用
     * @return
     */
    @PostMapping("/role/{roleId}/{status}")
    public R<String> updateRoleStatus(@PathVariable("roleId") Long roleId,@PathVariable("status") Integer status){
        return roleService.updateRoleStatus(roleId,status);
    }
}
