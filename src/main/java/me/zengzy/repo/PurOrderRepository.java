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

    @Query(value = "SELECT * FROM pur_orders WHERE order_status = 0 OR order_status = 1", nativeQuery = true)
    ArrayList<PurOrders> getAllUnRecOrder();

    @Query(value = "SELECT * FROM pur_orders WHERE pur_serial_no = :serial_no", nativeQuery = true)
    PurOrders getPurOrderBySerialNo(@Param("serial_no") int serial_no);

    @Modifying
    @Query(value = "UPDATE pur_orders SET order_status = :status WHERE pur_serial_no = :serial_no", nativeQuery = true)
    void updateOrderStatus(@Param("status") int status, @Param("serial_no") int serial_no);

    @Modifying
    @Query(value = "UPDATE pur_orders SET order_status = 1 WHERE pur_serial_no = :serial_no", nativeQuery = true)
    void recPurOrder(@Param("serial_no") int serial_no);

}
