package org.mmj.stock.vo.req;

import lombok.Data;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 10:59
 */
@Data
public class UserOwnRoleReqVo {

    private Long userId;

    private List<Long> roleIds;
}
