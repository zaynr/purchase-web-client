package me.zengzy.repo;

import me.zengzy.dto.ProOrders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ProOrderRepository extends CrudRepository<ProOrders, Long> {

}
