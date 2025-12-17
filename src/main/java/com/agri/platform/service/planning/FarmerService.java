package com.agri.platform.service;

import com.agri.platform.entity.Farmer;

public interface FarmerService {
    // 根据农场主ID查询
    Farmer getByFarmerId(Long farmerId);
    
    // 标准CRUD方法
    Farmer getById(Long id);
    boolean save(Farmer farmer);
    boolean update(Farmer farmer);
    boolean removeById(Long id);
}