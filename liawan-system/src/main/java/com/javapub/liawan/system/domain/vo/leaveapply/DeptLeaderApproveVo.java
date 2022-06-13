package com.javapub.liawan.system.domain.vo.leaveapply;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/6/13 16:15
 * @Version: 1.0
 * @Description: 部门领导审批Vo对象
 */
public class DeptLeaderApproveVo {

    private String userId;
    private String leaveType;
    private String startTime;
    private String endTime;
    private String reason;
    private String hr;
    private Boolean deptleaderapprove;
    private Boolean hrapprove;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public Boolean getDeptleaderapprove() {
        return deptleaderapprove;
    }

    public void setDeptleaderapprove(Boolean deptleaderapprove) {
        this.deptleaderapprove = deptleaderapprove;
    }

    public Boolean getHrapprove() {
        return hrapprove;
    }

    public void setHrapprove(Boolean hrapprove) {
        this.hrapprove = hrapprove;
    }
}
