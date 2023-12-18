package pl.adrian.amazon.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.adrian.amazon.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           return userRepository.findByEmail(username)
                   .map(user -> new UserDetailsImpl(user.getEmail(), user.getPassword()))
                   .orElseThrow(() -> new UsernameNotFoundException("User with email: " + username + " not found"));
    }
}
