package com.agri.platform.mapper.log;

import com.agri.platform.entity.log.AuditLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuditLogMapperTest {

    @Autowired
    private AuditLogMapper auditLogMapper;

    @Test
    public void testInsertAuditLog() {
        // 准备测试数据
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId("user-" + UUID.randomUUID().toString().substring(0, 8));
        auditLog.setOperation("CREATE");
        auditLog.setTargetType("USER");
        auditLog.setTargetId("12345");
        auditLog.setIp("192.168.1.100");

        // 执行插入操作
        auditLogMapper.insertAuditLog(auditLog);

        // 验证插入成功（由于没有返回ID的方法，我们验证其他查询方法）
        List<AuditLog> logsByUser = auditLogMapper.getAuditLogsByUserId(auditLog.getUserId());
        assertThat(logsByUser).isNotEmpty();
        AuditLog insertedLog = logsByUser.get(0);
        assertThat(insertedLog.getUserId()).isEqualTo(auditLog.getUserId());
        assertThat(insertedLog.getOperation()).isEqualTo(auditLog.getOperation());
        assertThat(insertedLog.getTargetType()).isEqualTo(auditLog.getTargetType());
        assertThat(insertedLog.getTargetId()).isEqualTo(auditLog.getTargetId());
        assertThat(insertedLog.getIp()).isEqualTo(auditLog.getIp());
    }

    @Test
    public void testGetAuditLogsByUserId() {
        // 准备测试数据
        String userId = "test-user-" + UUID.randomUUID().toString().substring(0, 8);

        AuditLog auditLog1 = new AuditLog();
        auditLog1.setUserId(userId);
        auditLog1.setOperation("CREATE");
        auditLog1.setTargetType("USER");
        auditLog1.setTargetId("1001");
        auditLog1.setIp("192.168.1.100");

        AuditLog auditLog2 = new AuditLog();
        auditLog2.setUserId(userId);
        auditLog2.setOperation("UPDATE");
        auditLog2.setTargetType("PROFILE");
        auditLog2.setTargetId("2001");
        auditLog2.setIp("192.168.1.101");

        // 插入测试数据
        auditLogMapper.insertAuditLog(auditLog1);
        auditLogMapper.insertAuditLog(auditLog2);

        // 查询并验证结果
        List<AuditLog> logs = auditLogMapper.getAuditLogsByUserId(userId);
        assertThat(logs).hasSize(2);

        // 验证第一条日志
        AuditLog log1 = logs.stream().filter(log -> "CREATE".equals(log.getOperation())).findFirst().orElse(null);
        assertThat(log1).isNotNull();
        assertThat(log1.getUserId()).isEqualTo(userId);
        assertThat(log1.getTargetId()).isEqualTo("1001");

        // 验证第二条日志
        AuditLog log2 = logs.stream().filter(log -> "UPDATE".equals(log.getOperation())).findFirst().orElse(null);
        assertThat(log2).isNotNull();
        assertThat(log2.getUserId()).isEqualTo(userId);
        assertThat(log2.getTargetId()).isEqualTo("2001");
    }

    @Test
    public void testGetAuditLogsByTarget() {
        // 准备测试数据
        String targetType = "PRODUCT";
        String targetId = "5001";

        AuditLog auditLog1 = new AuditLog();
        auditLog1.setUserId("user1");
        auditLog1.setOperation("CREATE");
        auditLog1.setTargetType(targetType);
        auditLog1.setTargetId(targetId);
        auditLog1.setIp("192.168.1.100");

        AuditLog auditLog2 = new AuditLog();
        auditLog2.setUserId("user2");
        auditLog2.setOperation("UPDATE");
        auditLog2.setTargetType(targetType);
        auditLog2.setTargetId(targetId);
        auditLog2.setIp("192.168.1.101");

        // 插入测试数据
        auditLogMapper.insertAuditLog(auditLog1);
        auditLogMapper.insertAuditLog(auditLog2);

        // 查询并验证结果
        List<AuditLog> logs = auditLogMapper.getAuditLogsByTarget(targetType, Long.parseLong(targetId));
        assertThat(logs).hasSize(2);

        for (AuditLog log : logs) {
            assertThat(log.getTargetType()).isEqualTo(targetType);
            assertThat(log.getTargetId()).isEqualTo(targetId);
        }
    }

    @Test
    public void testGetAuditLogsByOperation() {
        // 准备测试数据
        String operation = "DELETE";

        AuditLog auditLog1 = new AuditLog();
        auditLog1.setUserId("user1");
        auditLog1.setOperation(operation);
        auditLog1.setTargetType("ORDER");
        auditLog1.setTargetId("3001");
        auditLog1.setIp("192.168.1.100");

        AuditLog auditLog2 = new AuditLog();
        auditLog2.setUserId("user2");
        auditLog2.setOperation(operation);
        auditLog2.setTargetType("INVENTORY");
        auditLog2.setTargetId("4001");
        auditLog2.setIp("192.168.1.101");

        AuditLog auditLog3 = new AuditLog();
        auditLog3.setUserId("user3");
        auditLog3.setOperation("UPDATE");
        auditLog3.setTargetType("PROFILE");
        auditLog3.setTargetId("2001");
        auditLog3.setIp("192.168.1.102");

        // 插入测试数据
        auditLogMapper.insertAuditLog(auditLog1);
        auditLogMapper.insertAuditLog(auditLog2);
        auditLogMapper.insertAuditLog(auditLog3);

        // 查询并验证结果
        List<AuditLog> logs = auditLogMapper.getAuditLogsByOperation(operation);
        assertThat(logs).hasSize(2);

        for (AuditLog log : logs) {
            assertThat(log.getOperation()).isEqualTo(operation);
        }
    }

    @Test
    public void testGetAuditLogsByIp() {
        // 准备测试数据
        String ip = "10.0.0.1";

        AuditLog auditLog1 = new AuditLog();
        auditLog1.setUserId("user1");
        auditLog1.setOperation("CREATE");
        auditLog1.setTargetType("USER");
        auditLog1.setTargetId("1001");
        auditLog1.setIp(ip);

        AuditLog auditLog2 = new AuditLog();
        auditLog2.setUserId("user2");
        auditLog2.setOperation("LOGIN");
        auditLog2.setTargetType("SESSION");
        auditLog2.setTargetId("sess-001");
        auditLog2.setIp(ip);

        AuditLog auditLog3 = new AuditLog();
        auditLog3.setUserId("user3");
        auditLog3.setOperation("LOGOUT");
        auditLog3.setTargetType("SESSION");
        auditLog3.setTargetId("sess-002");
        auditLog3.setIp("192.168.1.100");

        // 插入测试数据
        auditLogMapper.insertAuditLog(auditLog1);
        auditLogMapper.insertAuditLog(auditLog2);
        auditLogMapper.insertAuditLog(auditLog3);

        // 查询并验证结果
        List<AuditLog> logs = auditLogMapper.getAuditLogsByIp(ip);
        assertThat(logs).hasSize(2);

        for (AuditLog log : logs) {
            assertThat(log.getIp()).isEqualTo(ip);
        }
    }

    @Test
    public void testListAllAuditLogs() {
        // 准备测试数据
        AuditLog auditLog1 = new AuditLog();
        auditLog1.setUserId("user1");
        auditLog1.setOperation("CREATE");
        auditLog1.setTargetType("USER");
        auditLog1.setTargetId("1001");
        auditLog1.setIp("192.168.1.100");

        AuditLog auditLog2 = new AuditLog();
        auditLog2.setUserId("user2");
        auditLog2.setOperation("UPDATE");
        auditLog2.setTargetType("PROFILE");
        auditLog2.setTargetId("2001");
        auditLog2.setIp("192.168.1.101");

        // 插入测试数据
        auditLogMapper.insertAuditLog(auditLog1);
        auditLogMapper.insertAuditLog(auditLog2);

        // 查询并验证结果
        List<AuditLog> logs = auditLogMapper.listAllAuditLogs();
        assertThat(logs).isNotEmpty();

        // 验证至少包含我们刚刚插入的两条记录
        long matchingLogs = logs.stream()
                .filter(log -> ("user1".equals(log.getUserId()) && "CREATE".equals(log.getOperation())) ||
                        ("user2".equals(log.getUserId()) && "UPDATE".equals(log.getOperation())))
                .count();
        assertThat(matchingLogs).isEqualTo(2);
    }
}