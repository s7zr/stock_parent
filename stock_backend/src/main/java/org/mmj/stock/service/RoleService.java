package org.mmj.stock.service;

import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.RolePageReqVo;
import org.springframework.stereotype.Service;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 14:57
 */

public interface RoleService {
    /**
     * 分页查询当前角色信息
     * @param vo
     * @return
     */
    public R<PageResult> queryPageRole(RolePageReqVo vo);
}
