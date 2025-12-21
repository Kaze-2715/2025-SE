package com.agri.platform.mapper.planning;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.agri.platform.entity.planning.WarningHandleRecord;

import java.util.List;

@Mapper
public interface WarningHandleRecordMapper {
    @Select("SELECT * FROM warning_handle_record")
    List<WarningHandleRecord> selectAll();
    
    @Select("SELECT * FROM warning_handle_record WHERE farmer_id = #{userId}")
    List<WarningHandleRecord> selectByUserId(String userId);
    
    @Select("SELECT * FROM warning_handle_record WHERE warning_id = #{warningId}")
    List<WarningHandleRecord> selectByWarningId(Long warningId); // 改回Long类型
    
    @Select("SELECT * FROM warning_handle_record WHERE warning_id = #{warningId} AND farmer_id = #{userId}")
    List<WarningHandleRecord> selectByWarningIdAndUserId(Long warningId, String userId);
    
    @Select("SELECT * FROM warning_handle_record WHERE record_id = #{recordId}")
    WarningHandleRecord selectById(Long recordId); // 改回Long类型
    
    @Options(useGeneratedKeys = true) // 改回true，使用自增ID
    @Insert("INSERT INTO warning_handle_record(record_id, warning_id, farmer_id, handle_measure, handle_time, effect_feedback) VALUES(#{recordId}, #{warningId}, #{farmerId}, #{handleMeasure}, #{handleTime}, #{effectFeedback})")
    int insert(WarningHandleRecord record);
    
    @Update("UPDATE warning_handle_record SET warning_id = #{warningId}, farmer_id = #{farmerId}, handle_measure = #{handleMeasure}, handle_time = #{handleTime}, effect_feedback = #{effectFeedback} WHERE record_id = #{recordId}")
    int update(WarningHandleRecord record);
    
    @Delete("DELETE FROM warning_handle_record WHERE record_id = #{recordId}")
    int deleteById(Long recordId); // 改回Long类型
}