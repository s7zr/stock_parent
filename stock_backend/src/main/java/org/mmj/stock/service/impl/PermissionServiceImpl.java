package org.mmj.stock.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mmj.stock.exception.BusinessException;
import org.mmj.stock.mapper.SysPermissionMapper;
import org.mmj.stock.mapper.SysRolePermissionMapper;
import org.mmj.stock.pojo.entity.SysPermission;
import org.mmj.stock.service.PermissionService;
import org.mmj.stock.utils.IdWorker;
import org.mmj.stock.vo.req.PermissionAddVo;
import org.mmj.stock.vo.req.PermissionUpdateVo;
import org.mmj.stock.vo.resp.PermissionRespNodeTreeVo;
import org.mmj.stock.vo.resp.PermissionRespNodeVo;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 15:38
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Override
    public R<List<PermissionRespNodeVo>> selectAllTree() {
        //获取所有权限信息
        List<SysPermission> permissions=this.sysPermissionMapper.findAll();
        //递归组装权限树状结构 不包含按钮
        List<PermissionRespNodeVo>  tree= this.getTree(permissions,0l,false);
        return R.ok(tree);
    }

    @Override
    public List<PermissionRespNodeVo> getTree(List<SysPermission> permissions, long pid, boolean isOnlyMenuType) {
        ArrayList<PermissionRespNodeVo> list = Lists.newArrayList();
        if (CollectionUtils.isEmpty(permissions)) {
            return list;
        }
        for (SysPermission permission : permissions) {
            if (permission.getPid().equals(pid)) {
                if (permission.getType().intValue()!=3 || !isOnlyMenuType) {
                    PermissionRespNodeVo respNodeVo = new PermissionRespNodeVo();
                    respNodeVo.setId(permission.getId());
                    respNodeVo.setTitle(permission.getTitle());
                    respNodeVo.setIcon(permission.getIcon());
                    respNodeVo.setPath(permission.getUrl());
                    respNodeVo.setName(permission.getName());
                    respNodeVo.setChildren(getTree(permissions,permission.getId(),isOnlyMenuType));
                    list.add(respNodeVo);
                }
            }
        }
        return list;
    }

    /**
     * 获取所有权限集合
     * @return
     */
    @Override
    public R<List<SysPermission>> getAll() {
        List<SysPermission> all = this.sysPermissionMapper.findAll();
        return R.ok(all);
    }
    /**
     * 添加权限时，回显权限树,不查看按钮
     * 保证构建的权限集合顺序与实际一致即可在页面顺序展示
     * @return
     */
    @Override
    public R<List<PermissionRespNodeTreeVo>> getAllPermissionTreeExBt() {
        //获取所有权限集合
        List<SysPermission> all = this.sysPermissionMapper.findAll();
        //构建权限树集合
        List<PermissionRespNodeTreeVo> result=new ArrayList<>();
        //构架顶级菜单（默认选项）
        PermissionRespNodeTreeVo root = new PermissionRespNodeTreeVo();
        root.setId(0l);
        root.setTitle("顶级菜单");
        root.setLevel(0);
        result.add(root);
        result.addAll(getPermissionLevelTree(all,0l,1));
        return R.ok(result);
    }
    /**
     * 递归设置级别，用于权限列表 添加/编辑 所属菜单树结构数据
     * @param permissions 权限集合
     * @param parentId 父级id
     * @param lavel 级别
     * @return
     */
    private List<PermissionRespNodeTreeVo> getPermissionLevelTree(List<SysPermission> permissions, Long parentId, int lavel) {
        List<PermissionRespNodeTreeVo> result=new ArrayList<>();
        for (SysPermission permission : permissions) {
            if (permission.getType().intValue()!=3 && permission.getPid().equals(parentId)) {
                PermissionRespNodeTreeVo nodeTreeVo = new PermissionRespNodeTreeVo();
                nodeTreeVo.setId(permission.getId());
                nodeTreeVo.setTitle(permission.getTitle());
                nodeTreeVo.setLevel(lavel);
                result.add(nodeTreeVo);
                result.addAll(getPermissionLevelTree(permissions,permission.getId(),lavel+1));
            }
        }
        return result;
    }
    /**
     * 权限添加按钮
     * @param vo
     * @return
     */
    @Override
    public R<String> addPermission(PermissionAddVo vo) {
        //检查当前提交数据是否合法,如果不合法，则抛出异常
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(vo,permission);
        this.checkPermissionForm(permission);
        //1.组装权限对象
        permission.setStatus(1);
        permission.setCreateTime(new Date());
        permission.setUpdateTime(new Date());
        permission.setDeleted(1);
        permission.setId(idWorker.nextId());
        //插入数据库
        int count = this.sysPermissionMapper.insert(permission);
        if (count!=1) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }
    /**
     * 更新权限
     * @param vo
     * @return
     */
    @Override
    public R<String> updatePermission(PermissionUpdateVo vo) {
        //检查当前提交数据是否合法,如果不合法，则抛出异常
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(vo,permission);
        this.checkPermissionForm(permission);
        //TODO 如果再更新时，父级已被修改，则排除异常
        permission.setStatus(1);
        permission.setUpdateTime(new Date());
        permission.setDeleted(1);
        int count = this.sysPermissionMapper.updateByPrimaryKeySelective(permission);
        if (count!=1) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }

    @Override
    public R<String> removePermission(Long permissionId) {
        //1.判断当前角色是否有角色自己，有则不能删除
        int count =this.sysPermissionMapper.findChildrenCountByParentId(permissionId);
        if (count>0) {
            throw new BusinessException(ResponseCode.ROLE_PERMISSION_RELATION.getMessage());
        }
        //2.删除角色关联权限的信息
        this.sysRolePermissionMapper.deleteByPermissionId(permissionId);
        //3.更新权限状态为已删除
        SysPermission permission = SysPermission.builder().id(permissionId).deleted(0).updateTime(new Date()).build();
        int updateCount = this.sysPermissionMapper.updateByPrimaryKeySelective(permission);
        if (updateCount!=1) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }
    /**
     * 根据用户id查询权限集合
     * @param userId
     * @return
     */
    @Override
    public List<SysPermission> getPermissionByUserId(Long userId) {
        return sysPermissionMapper.getPermissionByUserId(userId);
    }

    /**
     * 检查添加或者更新的权限提交表单是否合法，如果不合法，则直接抛出异常
     * 检查规则：目录的父目录等级必须为0或者其他目录（等级为1）
     *          菜单的父父级必须是1，也就是必须是父目录，
     *          按钮的父级必须是菜单，也是是等级是3，且父级是2
     *          其他关联的辨识 url等信息也可做相关检查
     * @param vo
     */
    private void checkPermissionForm(SysPermission vo) {
        if (vo!=null || vo.getType()!=null || vo.getPid()!=null){
            //获取权限类型 0：顶级目录 1.普通目录 2.菜单 3.按钮
            Integer type = vo.getType();
            //获取父级id
            Long pid = vo.getPid();
            //根据父级id查询父级信息
            SysPermission parentPermission = this.sysPermissionMapper.selectByPrimaryKey(pid);
            if (type==1){
                if(!pid.equals("0") || (parentPermission!=null && parentPermission.getType()> 1)){
                    throw new BusinessException(ResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR.getMessage());
                }
            }
            else if (type==2){
                if (parentPermission==null || parentPermission.getType() !=1 ){
                    throw new BusinessException(ResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR.getMessage());
                }
                if (StringUtils.isBlank(vo.getUrl())){
                    throw new BusinessException(ResponseCode.OPERATION_MENU_PERMISSION_URL_CODE_NULL.getMessage());
                }
            }
            else if (type==3){
                if (parentPermission==null || parentPermission.getType()!=2){
                    throw new BusinessException(ResponseCode.OPERATION_MENU_PERMISSION_BTN_ERROR.getMessage());
                }
                else if (vo.getUrl()==null || vo.getCode()==null || vo.getMethod()==null){
                    throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
                }
            }
            else {
                throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
            }
        }else {
            throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
        }
    }
}
