package me.zengzy.repo;

import me.zengzy.entity.Contract;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ContractRepository extends CrudRepository<Contract, Long>{
    @Query(value = "SELECT * FROM contracts WHERE contract_serial_no = :sn", nativeQuery = true)
    Contract getByPrimaryKey(@Param("sn") int sn);

    @Query(value = "SELECT * FROM contracts WHERE pro_serial_no = :sn", nativeQuery = true)
    Contract getByProSn(@Param("sn") int sn);

    @Query(value = "SELECT * FROM contracts WHERE pur_serial_no = :sn", nativeQuery = true)
    Contract getByPurSn(@Param("sn") int sn);
}
