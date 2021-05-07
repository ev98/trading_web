package com.ev.trading.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ev.trading.entity.User;
import com.ev.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
        * 1.通过username 获取到userInfo信息
        * 2.通过User(UserDetails)返回UserDetails
        * */

        System.out.println("-------------------"+username);

        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username",username);
        User userInfo = userService.getOne(wrapper);
        if (userInfo == null){
            throw  new UsernameNotFoundException("not found");
        }
        //定义权限列表
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+userInfo.getRole()));
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(userInfo.getUsername(), passwordEncoder.encode(userInfo.getPassword()), authorities);
        return userDetails;

    }
}
