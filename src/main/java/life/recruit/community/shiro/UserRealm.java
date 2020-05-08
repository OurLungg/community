package life.recruit.community.shiro;

import life.recruit.community.model.User;
import life.recruit.community.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");

        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //查询当前登录用户的授权字符串
        //principal从认证的逻辑来
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User dbUser = userService.findById(user.getId());
        //添加授权字符串
        info.addStringPermission(dbUser.getPerms());
        System.out.println("当前用户:" + dbUser.getUsername());
        System.out.println(dbUser.getPerms());
        return info;

    }


    /**
     * 执行认证逻辑
     * @param authenticationToken 刚才登录输入的账户和密码
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");

        //编写Shiro判断逻辑 判断用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findUserByname(token.getUsername());

        if(user == null){
            //用户不存在
            return null;
            //shiro底层会抛出UnKnowAccountException
        }
        //判断密码
        //第一个参数就是principal
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
