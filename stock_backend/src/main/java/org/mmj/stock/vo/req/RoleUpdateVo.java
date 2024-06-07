package org.mmj.stock.vo.req;

import lombok.Data;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 16:40
 */
@Data
public class RoleUpdateVo {
    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;

    /**
     * 权限ID集合
     */
    private List<Long> permissionsIds;
}
