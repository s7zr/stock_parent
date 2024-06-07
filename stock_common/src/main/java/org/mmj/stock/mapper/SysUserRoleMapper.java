package org.mmj.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.entity.SysUserRole;

import java.util.List;

/**
* @author mmj
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.SysUserRole
*/
public interface SysUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    /**
     * 根据用户id查询角色集合
     * @param userId
     * @return
     */
    List<Long> findRoleIdsByUserId(Long userId);
    /**
     * 根据用户id删除关联的角色
     * @param userId
     * @return
     */
    void deleteByUserId(@Param("userId") Long userId);
    /**
     * 批量插入信息
     * @param list
     * @return
     */
    int insertBatch(@Param("urs") List<SysUserRole> list);
}
