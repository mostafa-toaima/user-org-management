//package com.user_organization_management.security;
//import java.util.Collection;
//import java.util.List;
//
//import com.user_organization_management.entity.UserEntity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//
//public class UserDetailsServiceImpl implements UserDetails {
//    private String email;
//    private String password;
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public UserDetailsServiceImpl(String email, String password, Collection<? extends GrantedAuthority> authorities) {
//        this.email = email;
//        this.password = password;
//        this.authorities = authorities;
//    }
//
//    public static UserDetailsServiceImpl build(UserEntity user) {
//        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
//        return new UserDetailsServiceImpl(user.getEmail(), user.getPassword(), authorities);
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
