package org.mmj.stock.vo.req;

import lombok.Data;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 9:38
 */
@Data
public class UserAddReqVo {
    /**
     * 账户名称
     */
    private String  username;
    private String  password;
    private String  phone;
    private String  email;
    private String  nickName;
    private String  realName;
    /**
     * 性别 1：男 0：女
     */
    private Integer  sex;
    private Integer  createWhere;
    /**
     * 账户装填 1.正常 2. 锁定
     */
    private Integer  status;
}
