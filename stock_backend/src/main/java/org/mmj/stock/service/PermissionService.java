package org.mmj.stock.service;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.entity.SysPermission;
import org.mmj.stock.vo.req.PermissionAddVo;
import org.mmj.stock.vo.req.PermissionUpdateVo;
import org.mmj.stock.vo.resp.PermissionRespNodeTreeVo;
import org.mmj.stock.vo.resp.PermissionRespNodeVo;
import org.mmj.stock.vo.resp.R;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 15:37
 */
public interface PermissionService {
    /**
     * 权限数据显示（仅仅包含id title childen）
     * @return
     */
    R<List<PermissionRespNodeVo>> selectAllTree();
    /**
     * @param permissions 权限树状集合
     * @param pid 权限父id，顶级权限的pid默认为0
     * @param isOnlyMenuType true:遍历到菜单，  false:遍历到按钮
     * type: 目录1 菜单2 按钮3
     * @return
     */
    List<PermissionRespNodeVo> getTree(List<SysPermission> permissions, long pid, boolean isOnlyMenuType);
    /**
     * 查询所有权限集合
     * @return
     */
    R<List<SysPermission>> getAll();
    /**
     * 添加权限时，回显权限树
     * @return
     */
    R<List<PermissionRespNodeTreeVo>> getAllPermissionTreeExBt();
    /**
     * 权限添加按钮
     * @param vo
     * @return
     */
    R<String> addPermission(PermissionAddVo vo);
    /**
     * 更新权限
     * @param vo
     * @return
     */
    R<String> updatePermission(PermissionUpdateVo vo);
    /**
     * 根据权限id删除权限操作（逻辑删除）
     * @param permissionId
     * @return
     */
    R<String> removePermission(Long permissionId);
    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    List<SysPermission> getPermissionByUserId(@Param("userId") Long userId);
}
