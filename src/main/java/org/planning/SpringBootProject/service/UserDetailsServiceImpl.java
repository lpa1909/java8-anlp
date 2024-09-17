package org.planning.SpringBootProject.service;

import java.util.ArrayList;
import java.util.List;


import org.planning.SpringBootProject.dao.AccountDAO;
import org.planning.SpringBootProject.entity.Account;
import org.planning.SpringBootProject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        System.out.println("Account= " + account.getUserRole());

        if (account == null) {
            throw new UsernameNotFoundException("User " //
                    + username + " was not found in the database");
        }

        // EMPLOYEE,MANAGER,..
        String role = account.getUserRole();

        List<GrantedAuthority> grantList = new ArrayList<>();

        // ROLE_EMPLOYEE, ROLE_MANAGER
        GrantedAuthority authority = new SimpleGrantedAuthority(role);

        grantList.add(authority);

        boolean enabled = account.isActive();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        UserDetails userDetails = (UserDetails) new User(account.getUserName(), //
                account.getEncrytedPassword(), enabled, accountNonExpired, //
                credentialsNonExpired, accountNonLocked, grantList);

        System.out.println(userDetails.getUsername());

        return userDetails;
    }

}