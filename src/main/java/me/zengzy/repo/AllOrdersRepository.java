package me.zengzy.repo;

import me.zengzy.entity.AllOrders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface AllOrdersRepository extends CrudRepository<AllOrders, Long> {
    @Query(value = "SELECT * FROM all_orders WHERE serial_no = :serial_no", nativeQuery = true)
    AllOrders getBySerialNo(@Param("serial_no") int serial_no);

    @Query(value = "SELECT * FROM all_orders WHERE mobile_no = :mobile_no AND order_cat = 0 ORDER BY serial_no DESC LIMIT :cur, :pageSize", nativeQuery = true)
    ArrayList<AllOrders> getByPurName(@Param("mobile_no") String mobile_no, @Param("cur") int cur, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM all_orders WHERE mobile_no = :mobile_no AND order_cat = 1 ORDER BY serial_no DESC LIMIT :cur, :pageSize", nativeQuery = true)
    ArrayList<AllOrders> getByProName(@Param("mobile_no") String mobile_no, @Param("cur") int cur, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM all_orders ORDER BY serial_no DESC LIMIT :cur, :pageSize", nativeQuery = true)
    ArrayList<AllOrders> getAll(@Param("cur") int cur, @Param("pageSize") int pageSize);
}
