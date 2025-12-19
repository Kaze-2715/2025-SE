package com.agri.platform.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long landDbId; // 关联 Land.id
    private String advisorName;
    @Column(length = 5000)
    private String content;
    private LocalDateTime createdAt;


    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLandDbId() { return landDbId; }
    public void setLandDbId(Long landDbId) { this.landDbId = landDbId; }
    public String getAdvisorName() { return advisorName; }
    public void setAdvisorName(String advisorName) { this.advisorName = advisorName; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
