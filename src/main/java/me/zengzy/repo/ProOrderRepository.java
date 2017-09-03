package me.zengzy.repo;

import me.zengzy.entity.ProOrders;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
@Repository
public interface ProOrderRepository extends CrudRepository<ProOrders, Long> {
    @Modifying
    @Query(value = "UPDATE pro_orders SET offer_price = :price WHERE pur_serial_no = :no AND provider_name = :provider_name")
    void updateProOrderPrice(@Param("no") int pur_serial_no, @Param("provider_name") String provider_name, @Param("price") double price);

    @Query(value = "SELECT * FROM pro_orders WHERE pur_serial_no = :no", nativeQuery = true)
    ArrayList<ProOrders> getByPurSerialNo(@Param("no") int pur_serial_no);
}
