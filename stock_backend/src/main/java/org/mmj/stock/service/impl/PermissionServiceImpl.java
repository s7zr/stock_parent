package org.mmj.stock.service.impl;

import com.google.common.collect.Lists;
import org.mmj.stock.mapper.SysPermissionMapper;
import org.mmj.stock.pojo.entity.SysPermission;
import org.mmj.stock.service.PermissionService;
import org.mmj.stock.vo.resp.PermissionRespNodeTreeVo;
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

    /**
     * 获取所有权限集合
     * @return
     */
    @Override
    public R<List<SysPermission>> getAll() {
        List<SysPermission> all = this.sysPermissionMapper.findAll();
        return R.ok(all);
    }
    /**
     * 添加权限时，回显权限树,不查看按钮
     * 保证构建的权限集合顺序与实际一致即可在页面顺序展示
     * @return
     */
    @Override
    public R<List<PermissionRespNodeTreeVo>> getAllPermissionTreeExBt() {
        //获取所有权限集合
        List<SysPermission> all = this.sysPermissionMapper.findAll();
        //构建权限树集合
        List<PermissionRespNodeTreeVo> result=new ArrayList<>();
        //构架顶级菜单（默认选项）
        PermissionRespNodeTreeVo root = new PermissionRespNodeTreeVo();
        root.setId(0l);
        root.setTitle("顶级菜单");
        root.setLevel(0);
        result.add(root);
        result.addAll(getPermissionLevelTree(all,0l,1));
        return R.ok(result);
    }
    /**
     * 递归设置级别，用于权限列表 添加/编辑 所属菜单树结构数据
     * @param permissions 权限集合
     * @param parentId 父级id
     * @param lavel 级别
     * @return
     */
    private List<PermissionRespNodeTreeVo> getPermissionLevelTree(List<SysPermission> permissions, Long parentId, int lavel) {
        List<PermissionRespNodeTreeVo> result=new ArrayList<>();
        for (SysPermission permission : permissions) {
            if (permission.getType().intValue()!=3 && permission.getPid().equals(parentId)) {
                PermissionRespNodeTreeVo nodeTreeVo = new PermissionRespNodeTreeVo();
                nodeTreeVo.setId(permission.getId());
                nodeTreeVo.setTitle(permission.getTitle());
                nodeTreeVo.setLevel(lavel);
                result.add(nodeTreeVo);
                result.addAll(getPermissionLevelTree(permissions,permission.getId(),lavel+1));
            }
        }
        return result;
    }
}
