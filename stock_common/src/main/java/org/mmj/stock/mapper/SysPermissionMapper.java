package org.mmj.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.entity.SysPermission;

import java.util.List;

/**
* @author mmj
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.SysPermission
*/
public interface SysPermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
    /**
     * 获取所有权限集合
     * @return
     */
    List<SysPermission> findAll();
    /**
     * 根据权限父类id查询对应子集权限
     * @param permissionId
     * @return
     */
    int findChildrenCountByParentId(@Param("permissionId") Long permissionId);
}
