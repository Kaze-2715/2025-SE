package com.agri.platform.controller.planning;

import com.agri.platform.entity.planning.WarningHandleRecord;
import com.agri.platform.mapper.planning.WarningHandleRecordMapper;
import com.agri.platform.service.planning.WarningHandleRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/warning-handle")
public class WarningHandleRecordController {
    @Autowired
    private WarningHandleRecordService recordService;
    @Autowired
    private WarningHandleRecordMapper recordMapper;

    // 新增处理记录
    // 访问：POST http://localhost:8080/warning-handle/add
    @PostMapping("/add")
    public String addRecord(@RequestBody WarningHandleRecord record) {
        // 示例：record需包含warningId、farmerId、handleMeasure、handleTime
        boolean success = recordService.addHandleRecord(record);
        return success ? "处理记录提交成功！预警状态已更新为“已处理”" : "提交失败！";
    }

    // 按预警ID查询处理记录
    // 访问：GET http://localhost:8080/warning-handle/warning/1（1是预警ID）
    @GetMapping("/warning/{warningId}")
    public List<WarningHandleRecord> getByWarningId(@PathVariable Long warningId) {
        return recordMapper.selectByWarningId(warningId);
    }
}