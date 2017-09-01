package me.zengzy.repo;

import me.zengzy.dto.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<Users, Long> {
    @Modifying
    @Query(value = "INSERT INTO users(user_name, pwd, user_type) VALUES (:user_name, :pwd, :user_type)", nativeQuery = true)
    void createNewUser(@Param("user_name") String userName, @Param("pwd") String pwd, @Param("user_type") int userType);

    @Query(value = "SELECT * FROM users WHERE user_name = :user_name", nativeQuery = true)
    Users queryUserByName(@Param("user_name") String userName);
}
