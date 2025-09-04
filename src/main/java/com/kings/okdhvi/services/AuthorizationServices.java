package com.kings.okdhvi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServices implements UserDetailsService {
    @Autowired
    UsuarioService us;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return us.encontrarPorEmail(username);
    }
}
