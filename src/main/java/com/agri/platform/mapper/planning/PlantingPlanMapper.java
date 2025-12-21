package com.agri.platform.mapper.planning;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.agri.platform.entity.planning.PlantingPlan;

import java.util.List;

@Mapper // 标识为MyBatis Mapper接口
public interface PlantingPlanMapper {
        /* 1. 按农场主ID查列表 */
        @Select("SELECT plan_id        AS planId, " +
                        "       farmer_id      AS farmerId, " +
                        "       crop_type      AS cropType, " +
                        "       planting_area  AS plantingArea, " +
                        "       planting_time  AS plantingTime, " +
                        "       expected_yield AS expectedYield, " +
                        "       yield_basis    AS yieldBasis, " +
                        "       create_time    AS createTime " +
                        "FROM t_planting_plan " +
                        "WHERE farmer_id = #{farmerId}")
        List<PlantingPlan> selectByFarmerId(String farmerId);

        /* 2. 按主键查单条 */
        @Select("SELECT plan_id AS planId, " +
                        "       farmer_id      AS farmerId, " +
                        "       crop_type      AS cropType, " +
                        "       planting_area  AS plantingArea, " +
                        "       planting_time  AS plantingTime, " +
                        "       expected_yield AS expectedYield, " +
                        "       yield_basis    AS yieldBasis, " +
                        "       create_time    AS createTime " +
                        "FROM t_planting_plan " +
                        "WHERE plan_id = #{id}")
        PlantingPlan selectById(Long id);

        /* 3. 新增 */
        @Options(useGeneratedKeys = true, keyProperty = "id") // 主键回写进实体
        @Insert("INSERT INTO t_planting_plan(" +
                        "farmer_id, crop_type, planting_area, planting_time, expected_yield, yield_basis, create_time) "
                        +
                        "VALUES(#{farmerId}, #{cropType}, #{plantingArea}, #{plantingTime}, #{expectedYield}, #{yieldBasis}, #{createTime})")
        int insert(PlantingPlan plan);

        /* 4. 修改 */
        @Update("UPDATE t_planting_plan " +
                        "SET farmer_id     = #{farmerId}, " +
                        "    crop_type     = #{cropType}, " +
                        "    planting_area = #{plantingArea}, " +
                        "    planting_time = #{plantingTime}, " +
                        "    expected_yield= #{expectedYield}, " +
                        "    yield_basis   = #{yieldBasis}, " +
                        "    create_time   = #{createTime} " + // 这里当更新时间用，或你再换成 update_time 字段
                        "WHERE plan_id = #{id}")
        int update(PlantingPlan plan);

        /* 5. 删除 */
        @Delete("DELETE FROM t_planting_plan WHERE plan_id = #{id}")
        int deleteById(Long id);
}