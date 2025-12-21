package com.agri.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Land {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String landId;
    private Double area;
    private String soilType;
    private String attachmentPath;
    private String userId; // 新增用户ID字段

    // getters / setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLandId() { return landId; }
    public void setLandId(String landId) { this.landId = landId; }
    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }
    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }
    public String getAttachmentPath() { return attachmentPath; }
    public void setAttachmentPath(String attachmentPath) { this.attachmentPath = attachmentPath; }
}