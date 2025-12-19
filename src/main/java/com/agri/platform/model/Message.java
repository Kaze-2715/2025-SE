package com.agri.platform.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long landDbId;
    private String sender; // "owner" 或 "advisor"
    @Column(length = 2000)
    private String text;
    private LocalDateTime createdAt;


    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLandDbId() { return landDbId; }
    public void setLandDbId(Long landDbId) { this.landDbId = landDbId; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
