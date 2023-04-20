package org.ocm.security;

import lombok.RequiredArgsConstructor;
import org.ocm.rest.repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepository.findById(username).map(UserDetailsImpl::new).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
    }
}
