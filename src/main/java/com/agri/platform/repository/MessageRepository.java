package com.agri.platform.repository;

import com.agri.platform.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByLandDbIdOrderByCreatedAtAsc(Long landDbId);
}
