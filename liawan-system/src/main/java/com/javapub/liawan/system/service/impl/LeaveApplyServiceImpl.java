package com.javapub.liawan.system.service.impl;

import java.util.HashMap;
import java.util.List;

import com.javapub.liawan.common.utils.DateUtils;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javapub.liawan.system.mapper.LeaveApplyMapper;
import com.javapub.liawan.system.domain.LeaveApply;
import com.javapub.liawan.system.service.ILeaveApplyService;

/**
 * 请假Service业务层处理
 *
 * @author
 * @date 2022-06-10
 */
@Service
public class LeaveApplyServiceImpl implements ILeaveApplyService {
    private static final Logger logger = LoggerFactory.getLogger(LeaveApplyServiceImpl.class);

    @Autowired
    private LeaveApplyMapper leaveApplyMapper;

    // IdentityService主要负责用户的管理
    @Autowired
    IdentityService identityService;

    // RuntimeService主要负责一些动态的任务，如启动一个新的实例，通常一个流程可以对应多个实例。它也用于检索和储存一些流程变量，比如在单向网关组件中需要传递之前的选择。
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 查询请假
     *
     * @param id 请假主键
     * @return 请假
     */
    @Override
    public LeaveApply selectLeaveApplyById(Long id) {
        return leaveApplyMapper.selectLeaveApplyById(id);
    }

    /**
     * 查询请假列表
     *
     * @param leaveApply 请假
     * @return 请假
     */
    @Override
    public List<LeaveApply> selectLeaveApplyList(LeaveApply leaveApply) {
        return leaveApplyMapper.selectLeaveApplyList(leaveApply);
    }

    /**
     * 新增请假
     *
     * @param leaveApply 请假
     * @return 结果
     */
    @Override
    public int insertLeaveApply(LeaveApply leaveApply) {
        logger.info("leaveApply:{}", leaveApply);
        leaveApply.setCreateTime(DateUtils.getNowDate());
        leaveApply.setUpdateTime(DateUtils.getNowDate());
        int rows = leaveApplyMapper.insertLeaveApply(leaveApply);
        // 发起请假流程
        identityService.setAuthenticatedUserId(leaveApply.getUserId());
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("applyuserid", leaveApply.getUserId());
        variables.put("deptleader", leaveApply.getDeptleader());
        /**
         * 流程定义key
         * 业务id
         * 参数:请假人,部门领导审批人
         */
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("leave", String.valueOf(leaveApply.getId()), variables);
        return rows;
    }

    /**
     * 修改请假
     *
     * @param leaveApply 请假
     * @return 结果
     */
    @Override
    public int updateLeaveApply(LeaveApply leaveApply) {
        leaveApply.setUpdateTime(DateUtils.getNowDate());
        return leaveApplyMapper.updateLeaveApply(leaveApply);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假主键
     * @return 结果
     */
    @Override
    public int deleteLeaveApplyByIds(Long[] ids) {
        return leaveApplyMapper.deleteLeaveApplyByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假主键
     * @return 结果
     */
    @Override
    public int deleteLeaveApplyById(Long id) {
        return leaveApplyMapper.deleteLeaveApplyById(id);
    }
}
