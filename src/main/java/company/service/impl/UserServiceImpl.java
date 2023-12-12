package company.service.impl;

import company.dto.UserDTO;
import company.entity.User;
import company.mapper.UserMapper;
import company.repository.UserRepository;
import company.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream().filter(user -> !user.getIsDeleted()).map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDTO(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO user) {
      userRepository.save(userMapper.convertToEntity(user));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {

//        Find current user
        User user = userRepository.findByUserName(userDTO.getUserName());

//     Map updated user dto to entity object

        User convertedUser = userMapper.convertToEntity(userDTO);

//        Set id to converted object

        convertedUser.setId(user.getId());

//        Save updated user

        userRepository.save(convertedUser);



        return findByUserName(userDTO.getUserName());
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public void delete(String username) {
        User user = userRepository.findByUserName(username);
        user.setIsDeleted(true);
        userRepository.save(user);
    }


}
