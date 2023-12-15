package company.repository;

import company.dto.TaskDTO;
import company.dto.UserDTO;
import company.entity.Project;
import company.entity.Task;
import company.entity.User;
import company.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus <> 'COMPLETE'")
    int totalNonCompletedTasks(String projectCode);

//    @Query(value = "SELECT COUNT(*) FROM tasks t JOIN projects p on t.project_id = p.id WHERE p.projectCode = ?1 AND t.taskStatus = 'Completed'", nativeQuery = true)
    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus = 'COMPLETE'")
    int totalCompletedTasks(String projectCode);

    List<Task> findAllTasksByTaskStatusIsNotAndAssignedEmployee(Status status, User user);

    List<Task> findAllTasksByTaskStatusAndAssignedEmployee(Status status, User user);

    List<Task> findAllByProject(Project project);

    List<Task> findAllByAssignedEmployee(User userDTO);
}
