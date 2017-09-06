package me.zengzy.repo;

import me.zengzy.entity.UserAddress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserAddressRepository extends CrudRepository<UserAddress, Long> {
    @Query(value = "SELECT * FROM user_address WHERE mobile_no = :mobile_no AND user_type = :user_type", nativeQuery = true)
    UserAddress queryByPrimaryKey(@Param("mobile_no") String mobile_no, @Param("user_type") int user_type);
}
