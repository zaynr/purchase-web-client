package me.zengzy.repo;

import me.zengzy.entity.Contacts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface ContactRepository extends CrudRepository<Contacts, Long>{

    @Query(value = "SELECT * FROM contacts ORDER BY coop_count DESC LIMIT :cur, :pageSize", nativeQuery = true)
    ArrayList<Contacts> queryAll(@Param("cur") int cur, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM contacts WHERE provider_mobile_no = :mobile_no ORDER BY coop_count DESC LIMIT :cur, :pageSize", nativeQuery = true)
    ArrayList<Contacts> queryByProMobNo(@Param("mobile_no") String mobileNo, @Param("cur") int cur, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM contacts WHERE purchaser_mobile_no = :mobile_no ORDER BY coop_count DESC LIMIT :cur, :pageSize", nativeQuery = true)
    ArrayList<Contacts> queryByPurMobNo(@Param("mobile_no") String mobileNo, @Param("cur") int cur, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM contacts WHERE purchaser_mobile_no = :pur_mobile_no AND provider_mobile_no = :pro_mobile_no", nativeQuery = true)
    Contacts queryByBothMobNo(@Param("pur_mobile_no") String purMobileNo, @Param("pro_mobile_no") String proMobileNo);
}
