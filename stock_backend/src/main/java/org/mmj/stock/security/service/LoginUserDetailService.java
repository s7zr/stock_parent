//package org.mmj.stock.security.service;
//
//import org.springframework.security.core.userdetails.UserDetailsService;
//import com.google.common.base.Strings;
//import org.mmj.stock.mapper.SysPermissionMapper;
//import org.mmj.stock.mapper.SysRoleMapper;
//import org.mmj.stock.pojo.entity.SysPermission;
//import org.mmj.stock.pojo.entity.SysRole;
//import org.mmj.stock.pojo.entity.SysUser;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
///**
// * @author mmj
// * @Description 定义获取用户详情服务bean
// * @create 2024-06-05 10:44
// */
//@Service("userDetailsService ")
//public class LoginUserDetailService implements UserDetailsService {
//    @Autowired
//    private SysUserMapperExt sysUserMapperExt;
//
//    @Autowired
//    private SysPermissionMapper sysPermissionMapper;
//
//    @Autowired
//    private SysRoleMapper sysRoleMapper;
//
//    @Autowired
//    private PermissionService permissionService;
//
//
//    /**
//     * 当用户登录认证是，底层会自动调用MyUserDetailService#loadUserByUsername（）把登录的账户名称传入
//     * 根据用户名称获取用户的详情信息：用户名 加密密码 权限集合，还包含前端需要的侧边栏树 、前端需要的按钮权限标识的集合等
//     * @param loginName
//     * @return
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
//        //2.根据用户名查询用户信息
//        SysUser dbUser= sysUserMapperExt.findUserByUserName(username);
//        //3.判断查询的用户信息
//        if (dbUser==null) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//        //4.2 成功则返回用户的正常信息
//        //获取指定用户的权限集合 添加获取侧边栏数据和按钮权限的结合信息
//        List<SysPermission> permissions = permissionService.getPermissionByUserId(dbUser.getId());
//        //前端需要的获取树状权限菜单数据
//        List<PermissionRespNodeVo> tree = permissionService.getTree(permissions, 0l, true);
//        //前端需要的获取菜单按钮集合
//        List<String> authBtnPerms = permissions.stream()
//                .filter(per -> !Strings.isNullOrEmpty(per.getCode()) && per.getType() == 3)
//                .map(per -> per.getCode()).collect(Collectors.toList());
//        //5.组装后端需要的权限标识
//        //5.1 获取用户拥有的角色
//        List<SysRole> roles = sysRoleMapper.getRoleByUserId(dbUser.getId());
//        //5.2 将用户的权限标识和角色标识维护到权限集合中
//        List<String> perms=new ArrayList<>();
//        permissions.stream().forEach(per->{
//            if (StringUtils.isNotBlank(per.getPerms())) {
//                perms.add(per.getPerms());
//            }
//        });
//        roles.stream().forEach(role->{
//            perms.add("ROLE_"+role.getName());
//        });
//        String[] permStr=perms.toArray(new String[perms.size()]);
//        //5.3 将用户权限标识转化成权限对象集合
//        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(permStr);
//        //6.封装用户详情信息实体对象
//        LoginUserDetail loginUserDetail = new LoginUserDetail();
//        //将用户的id nickname等相同属性信息复制到详情对象中
//        BeanUtils.copyProperties(dbUser,loginUserDetail);
//        loginUserDetail.setMenus(tree);
//        loginUserDetail.setAuthorities(authorityList);
//        loginUserDetail.setPermissions(authBtnPerms);
//        return loginUserDetail;
//    }
//}
