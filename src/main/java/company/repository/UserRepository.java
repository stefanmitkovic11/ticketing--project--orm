package company.repository;

import company.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,String> {

    User findByUserName(String username);

    @Transactional
    void deleteByUserName(String username);
}
