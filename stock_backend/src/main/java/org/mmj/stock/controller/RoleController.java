package org.mmj.stock.controller;

import org.mmj.stock.service.RoleService;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.RolePageReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
