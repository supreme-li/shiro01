package com.k.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class Demo {

    public static void main(String[] args) {
        //1.创建SecurityManagerFactory工厂类
        IniSecurityManagerFactory securityManagerFactory =
                new IniSecurityManagerFactory("classpath:shiro-permission.ini");

        //2.创建SecurityManager
        SecurityManager securityManager = securityManagerFactory.getInstance();

        //3.将安全管理器交由SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //4.获取主体subject
        Subject subject = SecurityUtils.getSubject();

        //5.创建登录令牌Token
        UsernamePasswordToken token=new UsernamePasswordToken(
                "zs",
                "123"
        );

        //6.身份认证
        //org.apache.shiro.authc.UnknownAccountException 账号错误
        //org.apache.shiro.authc.IncorrectCredentialsException 密码错误
        try {
            subject.login(token);
            System.out.println("身份认证成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //7.角色认证
        try {
            if (subject.hasRole("role1")) {
                System.out.println("角色验证成功!");
            } else {
                System.out.println("角色验证失败!");
            }
//            subject.checkRole("role1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //8.权限验证
        try {
            if (subject.isPermitted("system:user:add")) {
                System.out.println("权限验证成功");
            } else {
                System.out.println("权限验证失败");
            }
//            subject.checkPermission("system:role:add");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //9.安全退出
        subject.logout();
    }
}
