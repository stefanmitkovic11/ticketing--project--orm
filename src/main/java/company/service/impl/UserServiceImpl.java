package company.service.impl;

import company.dto.ProjectDTO;
import company.dto.TaskDTO;
import company.dto.UserDTO;
import company.entity.User;
import company.mapper.UserMapper;
import company.repository.UserRepository;
import company.service.ProjectService;
import company.service.TaskService;
import company.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,@Lazy ProjectService projectService, TaskService taskService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> userList = userRepository.findAll(Sort.by("firstName"));
        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());

    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUserName(username);
        return userMapper.convertToDTO(user);
    }

    @Override
    public void save(UserDTO dto) {
        dto.setPassWord(passwordEncoder.encode(dto.getPassWord()));

        userRepository.save(userMapper.convertToEntity(dto));
    }

    @Override
    public UserDTO update(UserDTO dto) {

        //Find current user
        User user = userRepository.findByUserName(dto.getUserName());
        //Map updated user dto to entity object
        User convertedUser = userMapper.convertToEntity(dto);
        //set id to converted object
        convertedUser.setId(user.getId());
        //save updated user
        userRepository.save(convertedUser);

        return findByUserName(dto.getUserName());
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);

    }

    @Override
    public void delete(String username) {
        User user = userRepository.findByUserName(username);

        if (checkIfUserCanBeDeleted(userMapper.convertToDTO(user))) {
            user.setIsDeleted(true);
            user.setUserName(user.getUserName()+"-"+user.getId());
            userRepository.save(user);
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {

        List<User> users = userRepository.findAllByRoleDescriptionIgnoreCase(role);

        return users.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted(UserDTO userDTO) {

        return switch (userDTO.getRole().getDescription()) {
            case "Manager" -> {
                List<ProjectDTO> projectDTOList = projectService.listAllByAssignedManager(userDTO);
                yield projectDTOList.isEmpty();
            }
            case "Employee" -> {
                List<TaskDTO> taskDTOList = taskService.listAllByAssignedEmployee(userDTO);
                yield taskDTOList.isEmpty();
            }
            default -> true;
        };

    }


}
