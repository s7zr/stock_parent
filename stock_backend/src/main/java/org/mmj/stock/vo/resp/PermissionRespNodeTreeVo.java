package org.mmj.stock.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 17:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRespNodeTreeVo {
    /**
     * 权限ID
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String title;
    /**
     * 菜单等级 1.目录 2.菜单 3.按钮
     */
    private int level;
}
