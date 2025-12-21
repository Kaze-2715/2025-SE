package com.agri.platform.VO.report;

import java.io.Serializable;

/**
 * 版本差异视图对象
 * 简化：给出旧/新版本号与差异摘要字符串。具体字段级 diff 可在前端或专用服务实现。
 */
public class VersionDiffVO implements Serializable {
    private Integer fromVersion;
    private Integer toVersion;
    private String diffSummary;

    public Integer getFromVersion() { return fromVersion; }
    public void setFromVersion(Integer fromVersion) { this.fromVersion = fromVersion; }
    public Integer getToVersion() { return toVersion; }
    public void setToVersion(Integer toVersion) { this.toVersion = toVersion; }
    public String getDiffSummary() { return diffSummary; }
    public void setDiffSummary(String diffSummary) { this.diffSummary = diffSummary; }
}
