package company.service;

import company.dto.ProjectDTO;
import company.dto.TaskDTO;
import company.dto.UserDTO;
import company.enums.Status;

import java.util.List;

public interface TaskService {

    TaskDTO findById(Long id);
    List<TaskDTO> listAllTasks();
    void save(TaskDTO taskDTO);
    TaskDTO update(TaskDTO taskDTO);
    void deleteById(Long id);
    void deleteByProject(ProjectDTO projectDTO);
    int totalNonCompletedTask(String projectCode);
    int totalCompletedTask(String projectCode);
    List<TaskDTO> listAllTasksByStatusIsNot(Status status);
    List<TaskDTO> listAllTasksByStatus(Status status);
    void updateStatus(TaskDTO taskDTO);
    void completeByProject(ProjectDTO projectDTO);
    List<TaskDTO> listAllByAssignedEmployee(UserDTO userDTO);
}
