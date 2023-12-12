package company.service.impl;

import company.dto.UserDTO;
import company.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<UserDTO> findAllUsers() {
        return null;
    }

    @Override
    public UserDTO findByUserName(String username) {
        return null;
    }

    @Override
    public void save(UserDTO user) {

    }

    @Override
    public UserDTO update(UserDTO user) {
        return null;
    }

    @Override
    public void deleteByUserName(String username) {

    }
}
