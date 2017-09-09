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

    @Query(value = "SELECT * FROM all_orders WHERE FIND_IN_SET(order_status, :status_set) ORDER BY serial_no DESC LIMIT :cur, :pageSize", nativeQuery = true)
    ArrayList<AllOrders> getByStatusSet(@Param("status_set") String status_set, @Param("cur") int cur, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM all_orders WHERE FIND_IN_SET(order_status, :status_set) AND mobile_no = :mobile_no AND order_cat = :order_cat ORDER BY serial_no DESC LIMIT :cur, :pageSize", nativeQuery = true)
    ArrayList<AllOrders> getByCatAndNameAndStatusSet(@Param("status_set") String status_set, @Param("mobile_no") String mobile_no, @Param("order_cat") int order_cat, @Param("cur") int cur, @Param("pageSize") int pageSize);
}
