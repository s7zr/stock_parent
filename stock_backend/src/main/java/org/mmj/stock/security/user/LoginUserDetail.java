package org.mmj.stock.security.user;

import lombok.Data;
import org.mmj.stock.vo.resp.PermissionRespNodeVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 22:56
 */
@Data
public class LoginUserDetail implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
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
}
