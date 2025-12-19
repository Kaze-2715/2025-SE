package com.agri.platform.repository;


import com.agri.platform.model.Advice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdviceRepository extends JpaRepository<Advice, Long> {
    List<Advice> findByLandDbId(Long landDbId);
}
