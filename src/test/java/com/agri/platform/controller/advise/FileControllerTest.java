package com.agri.platform.controller.advise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

    @InjectMocks
    private FileController fileController;

    @Test
    void testGetFile_FileExists() throws IOException {
        // 准备测试数据
        String filename = "test-file.txt";
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
        
        // 由于 FileController 直接使用了硬编码的 "uploads/" 路径，我们需要创建该目录下的文件
        // 或者修改测试方法来模拟文件存在
        File uploadsDir = new File("uploads");
        uploadsDir.mkdirs();
        File testFile = new File(uploadsDir, filename);
        testFile.createNewFile();
        testFile.deleteOnExit();
        
        // 执行测试
        ResponseEntity<FileSystemResource> response = fileController.getFile(filename);
        
        // 验证结果
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testFile.getAbsolutePath(), response.getBody().getFile().getAbsolutePath());
        
        // 清理
        testFile.delete();
        uploadsDir.delete();
    }

    @Test
    void testGetFile_FileNotFound() {
        // 准备测试数据
        String filename = "non-existent-file.txt";
        
        // 确保文件不存在
        File uploadsDir = new File("uploads");
        File nonExistentFile = new File(uploadsDir, filename);
        if (nonExistentFile.exists()) {
            nonExistentFile.delete();
        }
        
        // 执行测试
        ResponseEntity<FileSystemResource> response = fileController.getFile(filename);
        
        // 验证结果
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}