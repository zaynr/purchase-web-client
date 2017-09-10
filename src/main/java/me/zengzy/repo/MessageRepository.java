package me.zengzy.repo;

import me.zengzy.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query(value = "SELECT * FROM message WHERE mobile_no = :mobile_no AND user_type = :user_type AND FIND_IN_SET(message_type_no, :typeSet)", nativeQuery = true)
    ArrayList<Message> getByPriKyeAndType(@Param("mobile_no") String mobile_no, @Param("user_type") int user_type, @Param("typeSet") String typeSet);
}
