package me.zengzy.repo;

import me.zengzy.entity.PurOrders;
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

    @Query(value = "SELECT * FROM pur_orders WHERE purchaser_name = :purchaser_name", nativeQuery = true)
    ArrayList<PurOrders> getOrderByName(@Param("purchaser_name") String name);

    @Query(value = "SELECT * FROM pur_orders WHERE order_status = :status", nativeQuery = true)
    ArrayList<PurOrders> getPurOrderByStatus(@Param("status") int status);

    @Query(value = "SELECT * FROM pur_orders WHERE purchaser_name = :purchaser_name AND order_status = :status", nativeQuery = true)
    ArrayList<PurOrders> getPurOrderByNameAndStatus(@Param("purchaser_name") String name, @Param("status") int status);

    @Query(value = "SELECT * FROM pur_orders WHERE purchaser_name = :purchaser_name AND order_status = :status ORDER BY pur_serial_no DESC LIMIT :cur, :pgSize", nativeQuery = true)
    ArrayList<PurOrders> getPurOrderByNameAndStatus(@Param("purchaser_name") String name, @Param("status") int status, @Param("cur") int cur, @Param("pgSize") int pgSize);

    @Query(value = "SELECT * FROM pur_orders WHERE purchaser_name = :purchaser_name AND FIND_IN_SET(order_status, :statusSet) ORDER BY pur_serial_no DESC LIMIT :cur, :pgSize", nativeQuery = true)
    ArrayList<PurOrders> getPurOrderByNameAndStatus(@Param("purchaser_name") String name, @Param("statusSet") String statusSet, @Param("cur") int cur, @Param("pgSize") int pgSize);

    @Query(value = "SELECT * FROM pur_orders WHERE purchaser_name = :purchaser_name AND FIND_IN_SET(order_status, :statusSet)", nativeQuery = true)
    ArrayList<PurOrders> getPurOrderByNameAndStatus(@Param("purchaser_name") String name, @Param("statusSet") String statusSet);

    @Query(value = "SELECT * FROM pur_orders WHERE pur_serial_no = :serial_no", nativeQuery = true)
    PurOrders getPurOrderBySerialNo(@Param("serial_no") int serial_no);

    @Modifying
    @Query(value = "UPDATE pur_orders SET order_status = :status WHERE pur_serial_no = :serial_no", nativeQuery = true)
    void updateOrderStatus(@Param("status") int status, @Param("serial_no") int serial_no);
}
