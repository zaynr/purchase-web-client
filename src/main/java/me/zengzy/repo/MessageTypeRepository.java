package me.zengzy.repo;

import me.zengzy.entity.MessageType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface MessageTypeRepository extends CrudRepository<MessageType, Long> {
    @Query(value = "SELECT * FROM message_type WHERE type_no = :type_no", nativeQuery = true)
    MessageType getByNo(@Param("type_no") int typeNo);

    @Query(value = "SELECT * FROM message_type WHERE receiver = :user_type", nativeQuery = true)
    ArrayList<MessageType> getByReceiver(@Param("user_type") int user_type);
}
