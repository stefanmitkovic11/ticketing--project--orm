package company.service.impl;

import company.dto.ProjectDTO;
import company.dto.TaskDTO;
import company.dto.UserDTO;
import company.entity.Task;
import company.entity.User;
import company.enums.Status;
import company.mapper.ProjectMapper;
import company.mapper.TaskMapper;
import company.mapper.UserMapper;
import company.repository.TaskRepository;
import company.repository.UserRepository;
import company.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper, UserRepository userRepository, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public TaskDTO findById(Long id) {
        return taskMapper.convertToDto(taskRepository.findById(id).get());
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO taskDTO) {
        taskDTO.setAssignedDate(LocalDate.now());
        taskDTO.setTaskStatus(Status.OPEN);
        taskRepository.save(taskMapper.convertToEntity(taskDTO));
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) {
        taskDTO.setTaskStatus(Status.OPEN);
        taskDTO.setAssignedDate(LocalDate.now());

        Task task = taskRepository.findById(taskDTO.getId()).get();

        Task convertedTask = taskMapper.convertToEntity(taskDTO);

        convertedTask.setId(task.getId());
        convertedTask.setTaskStatus(taskDTO.getTaskStatus() == null ? task.getTaskStatus() : taskDTO.getTaskStatus());
        convertedTask.setAssignedDate(task.getAssignedDate());

        taskRepository.save(convertedTask);

        return findById(taskDTO.getId());
    }

    @Override
    public void deleteById(Long id) {
        Task task = taskMapper.convertToEntity(findById(id));

        task.setIsDeleted(true);

        taskRepository.save(task);
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {
        List<TaskDTO> list = listAllByProject(projectDTO);
        list.forEach(task -> deleteById(task.getId()));
    }

    private List<TaskDTO> listAllByProject(ProjectDTO projectDTO) {
        List<Task> list = taskRepository.findAllByProject(projectMapper.convertToEntity(projectDTO));
        return list.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        User loggedInUser = userRepository.findByUserName("john@employee.com");
        List<Task> list = taskRepository.findAllTasksByTaskStatusIsNotAndAssignedEmployee(status, loggedInUser);
        return list.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        User loggedInUser = userRepository.findByUserName("john@employee.com");
        List<Task> list = taskRepository.findAllTasksByTaskStatusAndAssignedEmployee(status, loggedInUser);
        return list.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskDTO.getId()).get();

        task.setTaskStatus(taskDTO.getTaskStatus());
        taskRepository.save(task);
    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {
        List<TaskDTO> list = listAllByProject(projectDTO);
        list.forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });
    }

    @Override
    public List<TaskDTO> listAllByAssignedEmployee(UserDTO userDTO) {
        List<Task> list = taskRepository.findAllByAssignedEmployee(userMapper.convertToEntity(userDTO));
        return list.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }
}
