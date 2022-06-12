package com.javapub.liawan.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.javapub.liawan.common.annotation.Excel;
import com.javapub.liawan.common.core.domain.BaseEntity;

/**
 * 请假对象 leave_apply
 *
 * @author ruoyi
 * @date 2022-06-10
 */
public class LeaveApply extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 请假人
     */
    @Excel(name = "请假人")
    private String userId;

    /**
     * 起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String leaveType;

    /**
     * 原因
     */
    @Excel(name = "原因")
    private String reason;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /**
     * 实际起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际起始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realityStartTime;

    /**
     * 实际结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realityEndTime;

    /**
     * 部门领导审批人
     **/
    private String deptleader;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setRealityStartTime(Date realityStartTime) {
        this.realityStartTime = realityStartTime;
    }

    public Date getRealityStartTime() {
        return realityStartTime;
    }

    public void setRealityEndTime(Date realityEndTime) {
        this.realityEndTime = realityEndTime;
    }

    public Date getRealityEndTime() {
        return realityEndTime;
    }

    public String getDeptleader() {
        return deptleader;
    }

    public void setDeptleader(String deptleader) {
        this.deptleader = deptleader;
    }

    @Override
    public String toString() {
        return "LeaveApply{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", leaveType='" + leaveType + '\'' +
                ", reason='" + reason + '\'' +
                ", applyTime=" + applyTime +
                ", realityStartTime=" + realityStartTime +
                ", realityEndTime=" + realityEndTime +
                ", deptleader='" + deptleader + '\'' +
                '}';
    }
}
