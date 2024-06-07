package org.mmj.stock.vo.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 9:54
 */
@Data
public class UserEditReqVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String nickName;
    private String realName;
    private Integer sex;
    private Integer createWhere;
    private Integer status;
}
