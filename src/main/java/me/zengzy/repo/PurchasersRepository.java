package me.zengzy.repo;

import me.zengzy.entity.Purchasers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface PurchasersRepository extends CrudRepository<Purchasers, Long> {
    @Query(value = "SELECT * FROM purchasers WHERE mobile_no = :mobile_no", nativeQuery = true)
    Purchasers getPurchaserByMobileNo(@Param("mobile_no") String mobileNo);
}
