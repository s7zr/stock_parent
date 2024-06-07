package org.mmj.stock.vo.resp;

import lombok.Data;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 11:26
 */
@Data
public class UserInfoRespVo {
    private Long id;

    private String username;

    private String phone;

    private String nickName;

    private String realName;

    private Integer sex;

    private Integer status;

    private String email;
}