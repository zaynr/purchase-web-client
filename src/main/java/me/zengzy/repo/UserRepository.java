package me.zengzy.repo;

import me.zengzy.entity.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<Users, Long> {
    @Query(value = "SELECT * FROM users WHERE mobile_no = :mobile_no and user_type = :user_type", nativeQuery = true)
    Users queryUserByPriKey(@Param("mobile_no") String mobile_no, @Param("user_type") int userType);

    @Modifying
    @Query(value = "UPDATE users SET space_used = space_used + :space_used WHERE mobile_no = :mobile_no and user_type = :user_type", nativeQuery = true)
    void updateUsedSpace(@Param("mobile_no") String mobile_no, @Param("user_type") int userType, @Param("space_used") double space_used);
}
