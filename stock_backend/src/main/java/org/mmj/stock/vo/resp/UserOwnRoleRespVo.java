package org.mmj.stock.vo.resp;

import lombok.Data;
import org.mmj.stock.pojo.entity.SysRole;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 10:24
 */
@Data
public class UserOwnRoleRespVo {
    /**
     * 用户的角色id集合
     */
    private List<Long> ownRoleIds;
    /**
     * 所有角色集合
     */
    private List<SysRole> allRole;
}
