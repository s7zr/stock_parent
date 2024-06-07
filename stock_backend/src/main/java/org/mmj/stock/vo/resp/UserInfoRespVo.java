package org.mmj.stock.vo.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 11:26
 */
@Data
public class UserInfoRespVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String username;

    private String phone;

    private String nickName;

    private String realName;

    private Integer sex;

    private Integer status;

    private String email;
}