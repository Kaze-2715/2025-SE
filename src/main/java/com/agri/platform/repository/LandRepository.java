package com.agri.platform.repository;

import com.agri.platform.model.Land;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandRepository extends JpaRepository<Land, Long>{
    Optional<Land> findByLandId(String landId);
}
