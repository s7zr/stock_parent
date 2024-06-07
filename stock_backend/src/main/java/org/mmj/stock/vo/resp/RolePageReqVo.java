package org.mmj.stock.vo.resp;

import lombok.Data;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 14:56
 */
@Data
public class RolePageReqVo {
    /**
     * 当前页
     */
    private Integer pageNum=1;
    /**
     * 每页大小
     */
    private Integer pageSize=10;
}
