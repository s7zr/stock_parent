package org.mmj.stock.service;

import org.mmj.stock.vo.req.RoleAddVo;
import org.mmj.stock.vo.req.RoleUpdateVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.RolePageReqVo;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 14:57
 */

public interface RoleService {
    /**
     * 分页查询当前角色信息
     * @param vo
     * @return
     */
    public R<PageResult> queryPageRole(RolePageReqVo vo);
    /**
     * 添加角色和角色关联权限
     * @param vo
     * @return
     */
    R<String> addRoleWithPermissions(RoleAddVo vo);

    /**
     * 根据角色Id查找对应的权限id集合
     * @param roleId
     * @return
     */
    R<Set<String>> getPermissionIdsByRoleId(Long roleId);
    /**
     * 更新角色信息，包含角色关联的权限信息
     * @param vo
     * @return
     */
    R<String> updateRoleWithPermissions(RoleUpdateVo vo);
}
