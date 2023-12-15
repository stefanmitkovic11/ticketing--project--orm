package company.service.impl;

import company.dto.TaskDTO;
import company.entity.Task;
import company.enums.Status;
import company.mapper.TaskMapper;
import company.repository.TaskRepository;
import company.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
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
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }
}
