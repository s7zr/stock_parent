package org.mmj.stock.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-08 9:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRespVoExt {
    /**
     * 用户ID
     */
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 电话
     */
    private String phone;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别(1.男 2.女)
     */
    private Integer sex;

    /**
     * 账户状态(1.正常 2.锁定 )
     */
    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 权限树，不包含按钮相关权限信息
     */
    private List<PermissionRespNodeVo> menus;

    /**
     * 前端按钮权限集合
     */
    private List<String> permissions;

    /**
     * 响应前端token
     */
    private String accessToken;

}