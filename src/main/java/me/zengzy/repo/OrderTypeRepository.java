package me.zengzy.repo;

import me.zengzy.dto.OrderTypes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface OrderTypeRepository extends CrudRepository<OrderTypes, Long> {
    @Modifying
    @Query(value = "INSERT INTO order_types(type_content) VALUE(:content)", nativeQuery = true)
    void addNewType(@Param("content") String content);

    @Query(value = "SELECT * FROM order_types WHERE type_no = :no", nativeQuery = true)
    OrderTypes getTypeByNo(@Param("no") int no);

    @Query(value = "SELECT * FROM order_types", nativeQuery = true)
    ArrayList<OrderTypes> getAllTypes();
}
