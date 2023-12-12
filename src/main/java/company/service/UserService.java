package company.service;

import company.dto.UserDTO;
import company.entity.User;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface UserService {

    List<UserDTO> findAllUsers();

    UserDTO findByUserName(String username);

    void save(UserDTO user);

    UserDTO update(UserDTO user);

    void deleteByUserName(String username);
}
