package me.zengzy.repo;

import me.zengzy.dto.Providers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ProviderRepository extends CrudRepository<Providers, Long> {

}
