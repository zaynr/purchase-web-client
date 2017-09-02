package me.zengzy.repo;

import me.zengzy.dto.PurOrders;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface PurOrderRepository extends CrudRepository<PurOrders, Long> {

    @Query(value = "SELECT * FROM pur_orders WHERE purchaser_name = :name", nativeQuery = true)
    ArrayList<PurOrders> getOrderByName(@Param("name") String name);

}
