package com.javapub.liawan.system.mapper;

import java.util.List;

import com.javapub.liawan.system.domain.LeaveApply;

/**
 * 请假Mapper接口
 *
 * @author ruoyi
 * @date 2022-06-10
 */
public interface LeaveApplyMapper {
    /**
     * 查询请假
     *
     * @param id 请假主键
     * @return 请假
     */
    public LeaveApply selectLeaveApplyById(Long id);

    /**
     * 查询请假列表
     *
     * @param leaveApply 请假
     * @return 请假集合
     */
    public List<LeaveApply> selectLeaveApplyList(LeaveApply leaveApply);

    /**
     * 新增请假
     *
     * @param leaveApply 请假
     * @return 结果
     */
    public int insertLeaveApply(LeaveApply leaveApply);

    /**
     * 修改请假
     *
     * @param leaveApply 请假
     * @return 结果
     */
    public int updateLeaveApply(LeaveApply leaveApply);

    /**
     * 删除请假
     *
     * @param id 请假主键
     * @return 结果
     */
    public int deleteLeaveApplyById(Long id);

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLeaveApplyByIds(Long[] ids);
}
