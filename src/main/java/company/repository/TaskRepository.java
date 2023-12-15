package company.repository;

import company.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus <> 'Completed'")
    int totalNonCompletedTasks(String projectCode);

//    @Query(value = "SELECT COUNT(*) FROM tasks t JOIN projects p on t.project_id = p.id WHERE p.projectCode = ?1 AND t.taskStatus = 'Completed'", nativeQuery = true)
    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus = 'Completed'")
    int totalCompletedTasks(String projectCode);
}
