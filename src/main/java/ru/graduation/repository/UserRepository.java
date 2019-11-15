package ru.graduation.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.User;

@Transactional(readOnly = true)
//@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public interface UserRepository extends JpaRepository<User, Integer> {
    Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    User getByEmail(String email);
    //User getUserByEmail(String email);
}
