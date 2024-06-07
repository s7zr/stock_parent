package org.mmj.stock.controller;

import org.mmj.stock.service.PermissionService;
import org.mmj.stock.vo.resp.PermissionRespNodeVo;
import org.mmj.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
