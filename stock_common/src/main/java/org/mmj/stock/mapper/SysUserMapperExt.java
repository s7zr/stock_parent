package org.mmj.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.domain.UserQueryDomain;
import org.mmj.stock.pojo.entity.SysUser;

import java.util.List;

/**
* @author mmj
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.SysUser
*/
public interface SysUserMapperExt {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);


    /**
     * 根据用户名 昵称 以及时间范围综合查询1
     * @param username 用户名
     * @param nickName 昵称
     * @param startTime 起始创建时间
     * @param endTime 最终创建日期
     * @return
     */
    List<UserQueryDomain> pageUsers(@Param("userName") String username, @Param("nickName") String nickName, @Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 根据账户名称查询账户信息1
     * @param username 账户名称
     * @return
     */
    SysUser findUserByUserName(String username);
}
