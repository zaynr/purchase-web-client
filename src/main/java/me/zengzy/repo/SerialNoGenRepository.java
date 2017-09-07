package me.zengzy.repo;

import me.zengzy.entity.SerialNoGen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SerialNoGenRepository extends CrudRepository<SerialNoGen, Long>{
    @Query(value = "SELECT MAX(serial_no) AS serial_no FROM serial_no_gen", nativeQuery = true)
    SerialNoGen getSerialNo();
}
