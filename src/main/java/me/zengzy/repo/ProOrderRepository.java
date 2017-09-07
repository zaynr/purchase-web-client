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

    @Query(value = "SELECT * FROM pro_orders WHERE provider_name = :provider_name", nativeQuery = true)
    ArrayList<ProOrders> getByProviderName(@Param("provider_name") String provider_name);

    @Query(value = "SELECT * FROM pro_orders WHERE pro_serial_no = :no", nativeQuery = true)
    ProOrders getByProSerialNo(@Param("no") int pro_serial_no);

    @Query(value = "SELECT * FROM pro_orders WHERE pur_serial_no = :no AND provider_name = :provider_name", nativeQuery = true)
    ProOrders getByPurSalNoAndName(@Param("no") int pro_serial_no, @Param("provider_name") String provider_name);

    @Query(value = "SELECT * FROM pro_orders WHERE provider_name = :provider_name AND order_status = :status", nativeQuery = true)
    ArrayList<ProOrders> getProOrderByNameAndStatus(@Param("provider_name") String name, @Param("status") int status);

    @Query(value = "SELECT * FROM pro_orders WHERE pur_serial_no = :sn ORDER BY offer_price LIMIT :cur, :pgSize", nativeQuery = true)
    ArrayList<ProOrders> getByPurOrdSnIncre(@Param("sn") int sn, @Param("cur") int cur, @Param("pgSize") int pgSize);

    @Query(value = "SELECT * FROM pro_orders WHERE pur_serial_no = :sn ORDER BY ABS(offer_price - :exp) LIMIT :cur, :pgSize", nativeQuery = true)
    ArrayList<ProOrders> getByPurOrdSerWithExp(@Param("sn") int sn, @Param("exp") double exp, @Param("cur") int cur, @Param("pgSize") int pgSize);
}
