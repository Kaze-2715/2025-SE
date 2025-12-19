package com.agri.platform.controller.advise;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
@RestController
@RequestMapping("/api/files")
public class FileController {
        @GetMapping("/{filename}")
        public ResponseEntity<FileSystemResource> getFile(@PathVariable String filename) {
            File f = new File("uploads/" + filename);
            if (!f.exists()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(new FileSystemResource(f));
        }
}
