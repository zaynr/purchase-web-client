package me.zengzy.repo;

import me.zengzy.entity.AllOrders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AllOrdersRepository extends CrudRepository<AllOrders, Long> {
    @Query(value = "SELECT * FROM all_orders WHERE serial_no = :serial_no", nativeQuery = true)
    AllOrders getBySerialNo(@Param("serial_no") int serial_no);
}
