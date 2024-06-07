package org.mmj.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mmj.stock.mapper.SysRoleMapper;
import org.mmj.stock.pojo.entity.SysRole;
import org.mmj.stock.service.RoleService;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.RolePageReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 14:58
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * 分页查询当前角色信息
     * @param vo
     * @return
     */
    @Override
    public R<PageResult> queryPageRole(RolePageReqVo vo) {
        //1.组装分页参数
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        //2.调用mapper查询
        List<SysRole> roleList = this.sysRoleMapper.findAll();
        PageResult<SysRole> pageResult = new PageResult<>(new PageInfo<>(roleList));
        return R.ok(pageResult);
    }
}
