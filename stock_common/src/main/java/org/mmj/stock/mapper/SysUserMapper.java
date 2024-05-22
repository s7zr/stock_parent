package org.mmj.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.entity.SysUser;

import java.util.List;

/**
* @author mmj
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    SysUser getUserByUserName(@Param("userName") String userName);

    /**
     * 查询所有用户信息
     * @return
     */
    List<SysUser> findAll();


}
