package company.service.impl;

import company.entity.User;
import company.entity.common.UserPrincipal;
import company.repository.UserRepository;
import company.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);

        if(user == null) {
            throw new UsernameNotFoundException("This user does not exist");
        }


        return new UserPrincipal(user);
    }
}
