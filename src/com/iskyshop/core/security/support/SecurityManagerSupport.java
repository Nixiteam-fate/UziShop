package com.iskyshop.core.security.support;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.iskyshop.core.security.SecurityManager;
import com.iskyshop.foundation.domain.Res;
import com.iskyshop.foundation.domain.Role;
import com.iskyshop.foundation.domain.User;
import com.iskyshop.foundation.service.IResService;
import com.iskyshop.foundation.service.IUserService;

public class SecurityManagerSupport implements UserDetailsService, SecurityManager {

    @Autowired
    private IUserService userService;

    @Autowired
    private IResService resService;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public UserDetails loadUserByUsername(String data) throws UsernameNotFoundException, DataAccessException {
        String[] list = data.split(",");
        String userName = list[0];
        String loginRole = "user";
        if (list.length == 2) {
            loginRole = list[1];
        }
        Map params = new HashMap();
        params.put("userName", userName);
        List users = this.userService.query("select obj from User obj where obj.userName =:userName ", params, -1, -1);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User " + userName + " has no GrantedAuthority");
        }
        User user = (User) users.get(0);
        Set authorities = new HashSet();
        if ((!(user.getRoles().isEmpty())) && (user.getRoles() != null)) {
            Iterator roleIterator = user.getRoles().iterator();
            while (roleIterator.hasNext()) {
                Role role = (Role) roleIterator.next();
                if (loginRole.equalsIgnoreCase("ADMIN")) {
                    GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(role.getRoleCode().toUpperCase());
                    authorities.add(grantedAuthority);
                } else if (!(role.getType().equals("ADMIN"))) {
                    GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(role.getRoleCode().toUpperCase());
                    authorities.add(grantedAuthority);
                }
            }
        }

        GrantedAuthority[] auths = new GrantedAuthority[authorities.size()];
        user.setAuthorities((GrantedAuthority[]) authorities.toArray(auths));
        return user;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, String> loadUrlAuthorities() {
        Map urlAuthorities = new HashMap();
        Map params = new HashMap();
        params.put("type", "URL");
        List<Res> urlResources = this.resService
                .query("select obj from Res obj where obj.type = :type", params, -1, -1);
        for (Res res : urlResources) {
            urlAuthorities.put(res.getValue(), res.getRoleAuthorities());
        }
        return urlAuthorities;
    }
}