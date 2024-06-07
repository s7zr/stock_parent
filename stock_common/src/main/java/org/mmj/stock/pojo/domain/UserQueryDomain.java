package org.mmj.stock.pojo.domain;

import lombok.Data;
import org.mmj.stock.pojo.entity.SysUser;

/**
 * @author mmj
 * @Description 查询用户信息封装，包含用户基本信息和
 * @create 2024-06-06 21:19
 */
@Data
public class UserQueryDomain extends SysUser {
    private String createUserName;
    private String updateUserName;
}
