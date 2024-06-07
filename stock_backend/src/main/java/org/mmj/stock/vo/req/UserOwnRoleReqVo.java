package org.mmj.stock.vo.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 10:59
 */
@Data
public class UserOwnRoleReqVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private List<Long> roleIds;
}
