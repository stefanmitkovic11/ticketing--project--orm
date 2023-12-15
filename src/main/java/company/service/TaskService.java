package company.service;

import company.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO findById(Long id);
    List<TaskDTO> listAllTasks();
    void save(TaskDTO taskDTO);
    TaskDTO update(TaskDTO taskDTO);
    void deleteById(Long id);
    int totalNonCompletedTask(String projectCode);
    int totalCompletedTask(String projectCode);
}
