package org.mmj.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.entity.SysRolePermission;

import java.util.List;
import java.util.Set;

/**
* @author mmj
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.SysRolePermission
*/
public interface SysRolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);
    /**
     * 批量添加用户角色集合
     * @param rps
     * @return
     */
    int addRolePermissionBatch(@Param("rps") List<SysRolePermission> rps);
    /**
     * 根据角色id查询对应的权限id集合
     * @param roleId 角色id
     * @return
     */
    Set<String> getPermissionIdsByRoleId(Long roleId);
    /**
     * 根据角色id删除关联的权限信息
     * @param id 角色id
     * @return
     */
    void deleteByRoleId(@Param("roleId") Long id);
}
