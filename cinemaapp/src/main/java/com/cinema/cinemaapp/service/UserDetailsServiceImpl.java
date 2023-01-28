package com.cinema.cinemaapp.service;
import com.cinema.cinemaapp.model.User;
import com.cinema.cinemaapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), buildSimpleGrantedAuthorities(user));

    }

    private List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(User user) {
//        List<Role> userRoles = user.getRoleList();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : userRoles){
//            authorities.add(new SimpleGrantedAuthority(role.getRoleType().name()));
//        }
//        return authorities;
        return user.getRoleList().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleType().name())).toList();

    }


}
