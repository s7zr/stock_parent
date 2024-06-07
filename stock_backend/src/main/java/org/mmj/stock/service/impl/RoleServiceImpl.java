package org.mmj.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mmj.stock.exception.BusinessException;
import org.mmj.stock.mapper.SysRoleMapper;
import org.mmj.stock.mapper.SysRolePermissionMapper;
import org.mmj.stock.pojo.entity.SysRole;
import org.mmj.stock.pojo.entity.SysRolePermission;
import org.mmj.stock.service.RoleService;
import org.mmj.stock.utils.IdWorker;
import org.mmj.stock.vo.req.RoleAddVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.ResponseCode;
import org.mmj.stock.vo.resp.RolePageReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 14:58
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
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
    /**
     * 添加角色和角色关联权限
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addRoleWithPermissions(RoleAddVo vo) {
        //分两步，先创建角色，再关联权限
        //角色ID
        Long roleId=idWorker.nextId();
        SysRole role = SysRole.builder().id(roleId)
                .deleted(1).name(vo.getName()).description(vo.getDescription())
                .createTime(new Date()).updateTime(new Date()).status(1).build();
        //添加角色
        int addRoleCount = sysRoleMapper.insert(role);
        if (addRoleCount !=1) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        //2.批量添加角色关联权限集合
        List<Long> permissionsIds = vo.getPermissionsIds();
        //流式编程将permissionIds映射成为关系表对象
        if (!CollectionUtils.isEmpty(permissionsIds)) {
            List<SysRolePermission> rps = permissionsIds.stream().map(permissionId -> {
                SysRolePermission rp = SysRolePermission.builder().id(idWorker.nextId()).roleId(roleId)
                        .permissionId(permissionId).createTime(new Date()).build();
                return rp;
            }).collect(Collectors.toList());
            //批量插入角色权限集合
            int counts=this.sysRolePermissionMapper.addRolePermissionBatch(rps);
            if (counts==0) {
                throw new BusinessException(ResponseCode.ERROR.getMessage());
            }
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }
}
