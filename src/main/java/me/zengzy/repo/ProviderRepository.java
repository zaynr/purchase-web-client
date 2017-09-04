package me.zengzy.repo;

import me.zengzy.entity.Providers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ProviderRepository extends CrudRepository<Providers, Long> {
    @Query(value = "SELECT * FROM providers WHERE mobile_no = :mobile_no", nativeQuery = true)
    Providers getProviderByMobileNo(@Param("mobile_no") String mobileNo);
}
