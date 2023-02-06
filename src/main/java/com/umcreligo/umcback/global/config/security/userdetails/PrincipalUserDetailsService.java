package com.umcreligo.umcback.global.config.security.userdetails;

import com.umcreligo.umcback.domain.user.domain.User;
import com.umcreligo.umcback.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String oauthId) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(oauthId)
                .orElseThrow(() -> new UsernameNotFoundException("가입된 회원이 존재하지 않습니다."));
        return new PrincipalUserDetails(user);
    }
}
