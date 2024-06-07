package org.mmj.stock.service;

import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.vo.req.*;
import org.mmj.stock.vo.resp.LoginRespVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.UserOwnRoleRespVo;

import java.util.List;
import java.util.Map;

public interface UserServiceExt {
    /**
     * 多条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @param userPageReqVo
     * @return
     */
    R<PageResult> pageUsers(UserPageReqVo userPageReqVo);

    /**
     * 添加用户信息
     * @param vo
     * @return
     */
    R<String> addUser(UserAddReqVo vo);

    /**
     * 更新用户信息
     * @param vo
     * @return
     */
    R<String> updateUser(UserEditReqVO vo);
    /**
     * 获取用户具有的角色信息，以及所有角色信息
     * @param userId
     * @return
     */
    R<UserOwnRoleRespVo> getUserOwnRole(Long userId);
    /**
     * 更新用户角色信息
     * @param vo
     * @return
     */
    R<String> updateUserOwnRoles(UserOwnRoleReqVo vo);

    /**
     * 批量删除角色
     * @param userIds
     * @return
     */
    R<String> deleteUsers(List<Long> userIds);
}
