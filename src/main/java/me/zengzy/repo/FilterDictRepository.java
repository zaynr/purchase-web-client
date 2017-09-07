package me.zengzy.repo;

import me.zengzy.entity.FilterDict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface FilterDictRepository extends CrudRepository<FilterDict, Long>{
    @Query(value = "SELECT * FROM filter_dict WHERE order_serial_no = :order_serial_no", nativeQuery = true)
    ArrayList<FilterDict> getByOrderSerialNo(@Param("order_serial_no") int order_serial_no);
}
