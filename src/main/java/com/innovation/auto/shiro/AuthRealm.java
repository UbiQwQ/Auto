package com.innovation.auto.shiro;

import com.innovation.auto.entity.User;
import com.innovation.auto.service.UserService;
import io.lettuce.core.models.role.RedisInstance;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: carver
 * @Date: 2018/10/23 15:54
 * @email lt1550919167@163.com
 * @QQ 1550919167
 * @Description:
 */
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 为用户授权
     * @param principals
     * @return
     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        //获取前端输入的用户信息，封装为User对象
//        User userweb = (User) principals.getPrimaryPrincipal();
//        //获取前端输入的用户名
//        String username = userweb.getName();
//        //根据前端输入的用户名查询数据库中对应的记录
//        User user = userService.selectByUsername(username);
//        //如果数据库中有该用户名对应的记录，就进行授权操作
//        if (user != null){
//            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//            //因为addRoles和addStringPermissions方法需要的参数类型是Collection
//            //所以先创建两个collection集合
//            Collection<String> rolesCollection = new HashSet<String>();
//            Collection<String> perStringCollection = new HashSet<String>();
//            //获取user的Role的set集合
//            Set<RedisInstance.Role> roles = user.getRoles();
//            //遍历集合
//            for (RedisInstance.Role role : roles){
//                //将每一个role的name装进collection集合
//                rolesCollection.add(role.getName());
//                //获取每一个Role的permission的set集合
//                Set<Permission> permissionSet =  role.getPermissions();
//                //遍历集合
//                for (Permission permission : permissionSet){
//                    //将每一个permission的name装进collection集合
//                    perStringCollection.add(permission.getName());
//                }
//                //为用户授权
//                info.addStringPermissions(perStringCollection);
//            }
//            //为用户授予角色
//            info.addRoles(rolesCollection);
//            return info;
//        }else{
//            return null;
//        }
//
//    }



    /**
     * 认证登录
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token携带了用户信息
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取前端输入的用户名
        String userName  = usernamePasswordToken.getUsername();
        //根据用户名查询数据库中对应的记录
        User user = userService.selectByUsername(userName);
        //当前realm对象的name
        String realmName = getName();
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getName());
        //封装用户信息，构建AuthenticationInfo对象并返回
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
                credentialsSalt, realmName);
        return authcInfo;
    }

    /**
     * SUBJECT授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}