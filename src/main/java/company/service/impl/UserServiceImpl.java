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
        return userRepository.findAll().stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDto(userRepository.findById(username).get());
    }

    @Override
    public void save(UserDTO user) {
      this.userRepository.save(userMapper.convertToEntity(user));
    }

    @Override
    public UserDTO update(UserDTO user) {
        return null;
    }

    @Override
    public void deleteByUserName(String username) {

    }
}
