package me.zengzy.repo;

import me.zengzy.entity.AdminOption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface AdminOptionRepository extends CrudRepository<AdminOption, Long>{
    @Query(value = "SELECT * FROM admin_option WHERE option_no = :option_no", nativeQuery = true)
    AdminOption queryByPrimaryKey(@Param("option_no") int option_no);
}
