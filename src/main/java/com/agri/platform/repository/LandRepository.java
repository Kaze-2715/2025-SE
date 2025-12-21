package com.agri.platform.repository;

import com.agri.platform.model.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface LandRepository extends JpaRepository<Land, Long>{
    Optional<Land> findByLandId(String landId);
    List<Land> findByUserId(String userId); // 新增按用户ID查询
    Optional<Land> findByIdAndUserId(Long id, String userId); // 新增按ID和用户ID查询
}