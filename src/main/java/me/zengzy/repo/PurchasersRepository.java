package me.zengzy.repo;

import me.zengzy.entity.Purchasers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface PurchasersRepository extends CrudRepository<Purchasers, Long> {
}
