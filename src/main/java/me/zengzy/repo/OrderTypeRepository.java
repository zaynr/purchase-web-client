package me.zengzy.repo;

import me.zengzy.entity.OrderTypes;
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

    @Query(value = "SELECT * FROM order_types WHERE type_no = :type_no", nativeQuery = true)
    OrderTypes getTypeByNo(@Param("type_no") int no);

    @Query(value = "SELECT * FROM order_types", nativeQuery = true)
    ArrayList<OrderTypes> getAllTypes();

    @Query(value = "SELECT * FROM order_types WHERE type_content LIKE %:content%", nativeQuery = true)
    ArrayList<OrderTypes> patternMatch(@Param("content") String content);

    @Query(value = "SELECT * FROM order_types ORDER BY type_no LIMIT 0, 3", nativeQuery = true)
    ArrayList<OrderTypes> getLimitedTypes();
}
