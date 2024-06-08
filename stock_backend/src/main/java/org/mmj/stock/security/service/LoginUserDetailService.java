package org.mmj.stock.security.service;

import org.mmj.stock.mapper.SysUserMapperExt;
import org.mmj.stock.security.user.LoginUserDetail;
import org.mmj.stock.service.PermissionService;
import org.mmj.stock.vo.resp.PermissionRespNodeVo;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.google.common.base.Strings;
import org.mmj.stock.mapper.SysPermissionMapper;
import org.mmj.stock.mapper.SysRoleMapper;
import org.mmj.stock.pojo.entity.SysPermission;
import org.mmj.stock.pojo.entity.SysRole;
import org.mmj.stock.pojo.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author mmj
 * @Description 定义获取用户详情服务bean
 * @create 2024-06-05 10:44
 */
@Service("userDetailsService ")
public class LoginUserDetailService implements UserDetailsService {
    @Autowired
    private SysUserMapperExt sysUserMapperExt;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private PermissionService permissionService;


    /**
     * 当用户登录认证是，底层会自动调用MyUserDetailService#loadUserByUsername（）把登录的账户名称传入
     * 根据用户名称获取用户的详情信息：用户名 加密密码 权限集合，还包含前端需要的侧边栏树 、前端需要的按钮权限标识的集合等
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser dbUser= sysUserMapperExt.findUserByUserName(username);
        if (dbUser==null) {
            throw  new UsernameNotFoundException("用户不存在");
        }
        //获取指定用户的权限集合 添加获取侧边栏数据和按钮权限的结合信息
        List<SysPermission> permissions = permissionService.getPermissionByUserId(dbUser.getId());
        //获取用户角色
        List<SysRole> roles = sysRoleMapper.getRoleByUserId(dbUser.getId());
        //获取树状权限菜单数据
        List<PermissionRespNodeVo> menus = permissionService.getTree(permissions, 0l, true);
        //获取菜单按钮集合
        List<String> authBtnPerms = permissions.stream()
                .filter(per -> !Strings.isNullOrEmpty(per.getCode()) && per.getType() == 3)
                .map(per -> per.getCode()).collect(Collectors.toList());
        //获取springSecurity的权限表示 ROLE_角色名称 + 权限自身表示
        ArrayList<String> ps = new ArrayList<>();
        List<String> pers = permissions.stream().filter(per -> StringUtils.isNotBlank(per.getPerms()))
                .map(per -> per.getPerms())
                .collect(Collectors.toList());
        ps.addAll(pers);
        List<String> rs = roles.stream().map(r->"ROLE_"+r.getName()).collect(Collectors.toList());
        ps.addAll(rs);
        //将用户拥有的权限表示转权限对象
        String[] psArray = ps.toArray(new String[pers.size()]);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(psArray);
        //构建用户详情服务对象
        LoginUserDetail userDetail = new LoginUserDetail();
        BeanUtils.copyProperties(dbUser,userDetail);
        userDetail.setMenus(menus);
        userDetail.setPermissions(authBtnPerms);
        userDetail.setAuthorities(authorityList);
        return userDetail;
    }
}
