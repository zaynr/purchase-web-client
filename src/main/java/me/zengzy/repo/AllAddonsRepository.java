package me.zengzy.repo;

import me.zengzy.entity.AllAddons;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface AllAddonsRepository extends CrudRepository<AllAddons, Long> {
    @Query(value = "SELECT * FROM all_addons WHERE order_serial_no = :order_serial_no", nativeQuery = true)
    ArrayList<AllAddons> queryByOrderSerialNo(@Param("order_serial_no") int orderSerialNo);

    @Query(value = "SELECT * FROM all_addons WHERE addon_serial_no = :serial_no", nativeQuery = true)
    AllAddons queryByPrimaryKey(@Param("serial_no") int serial_no);
}
