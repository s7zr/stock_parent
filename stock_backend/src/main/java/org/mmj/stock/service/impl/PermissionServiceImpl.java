package org.mmj.stock.service.impl;

import com.google.common.collect.Lists;
import org.mmj.stock.mapper.SysPermissionMapper;
import org.mmj.stock.pojo.entity.SysPermission;
import org.mmj.stock.service.PermissionService;
import org.mmj.stock.vo.resp.PermissionRespNodeVo;
import org.mmj.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 15:38
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Override
    public R<List<PermissionRespNodeVo>> selectAllTree() {
        //获取所有权限信息
        List<SysPermission> permissions=this.sysPermissionMapper.findAll();
        //递归组装权限树状结构 不包含按钮
        List<PermissionRespNodeVo>  tree= this.getTree(permissions,0l,false);
        return R.ok(tree);
    }

    @Override
    public List<PermissionRespNodeVo> getTree(List<SysPermission> permissions, long pid, boolean isOnlyMenuType) {
        ArrayList<PermissionRespNodeVo> list = Lists.newArrayList();
        if (CollectionUtils.isEmpty(permissions)) {
            return list;
        }
        for (SysPermission permission : permissions) {
            if (permission.getPid().equals(pid)) {
                if (permission.getType().intValue()!=3 || !isOnlyMenuType) {
                    PermissionRespNodeVo respNodeVo = new PermissionRespNodeVo();
                    respNodeVo.setId(permission.getId());
                    respNodeVo.setTitle(permission.getTitle());
                    respNodeVo.setIcon(permission.getIcon());
                    respNodeVo.setPath(permission.getUrl());
                    respNodeVo.setName(permission.getName());
                    respNodeVo.setChildren(getTree(permissions,permission.getId(),isOnlyMenuType));
                    list.add(respNodeVo);
                }
            }
        }
        return list;
    }
}
