package com.agri.platform.service.planning;

import com.agri.platform.entity.planning.PestWarning;
import com.agri.platform.entity.planning.WarningHandleRecord;
import com.agri.platform.mapper.planning.PestWarningMapper;
import com.agri.platform.mapper.planning.WarningHandleRecordMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class WarningHandleRecordService {
    @Autowired
    private WarningHandleRecordMapper recordMapper;
    @Autowired
    private PestWarningMapper warningMapper;

    // 新增处理记录，并更新预警状态为"已处理"（事务保证原子性）
    @Transactional
    public boolean addHandleRecord(WarningHandleRecord record) {
        // 1. 保存处理记录
        int recordRows = recordMapper.insert(record);
        if (recordRows <= 0) return false;

        // 2. 更新对应预警的状态为"已处理"（2）
        // 修改这里：将updateById改为update
        PestWarning warning = new PestWarning();
        warning.setWarningId(record.getWarningId());
        warning.setHandleStatus(2); // 2=已处理
        warning.setUpdateTime(LocalDateTime.now());
        warningMapper.update(warning);  // 修改这一行

        return true;
    }
}