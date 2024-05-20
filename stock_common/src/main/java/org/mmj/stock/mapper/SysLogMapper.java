package org.mmj.stock.mapper;

import org.mmj.stock.pojo.entity.SysLog;

/**
* @author mmj
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.SysLog
*/
public interface SysLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

}
