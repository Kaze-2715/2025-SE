package com.agri.platform.mapper.report;

import com.agri.platform.entity.report.Report;
import com.agri.platform.DTO.report.ReportQueryDTO;
import com.agri.platform.VO.report.ReportListVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报告主表 Mapper
 * 对应数据库表: report
 */
@Mapper
public interface ReportMapper {

    /**
     * 新增报告
     * 对应功能：1-6 报告生成
     */
    int insert(Report report);

    /**
     * 根据ID查询报告详情
     * 对应功能：查看报告详情
     */
    Report selectById(@Param("id") Long id);

    /**
     * 更新报告内容或状态
     * 对应功能：7 报告编辑
     */
    int update(Report report);

    /**
     * 综合条件查询列表
     * 对应功能：10 查询功能
     * 注意：此方法通常需要在XML中编写动态SQL，根据DTO中的非空字段进行筛选
     */
    List<Report> selectByCondition(@Param("query") ReportQueryDTO query);

    /**
     * 删除报告 (可选功能)
     */
    int deleteById(@Param("id") Long id);

    // 带访问控制的查询（当前用户可见的报告：本人创建或分享给本人；管理员不受限）
    Report selectByIdWithAcl(Long id, Long currentUserId);

    int updateLatestVersion(Long id, Integer latestVersion);

    int archive(Long reportId, Long operatorId);

    List<ReportListVO> queryList(ReportQueryDTO query);
}
