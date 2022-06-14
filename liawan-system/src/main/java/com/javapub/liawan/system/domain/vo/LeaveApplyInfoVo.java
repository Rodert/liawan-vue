package com.javapub.liawan.system.domain.vo;

import com.javapub.liawan.common.core.domain.entity.SysUser;
import com.javapub.liawan.system.domain.LeaveApply;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/6/12 18:02
 * @Version: 1.0
 * @Description:
 */

public class LeaveApplyInfoVo {

    private LeaveApply leaveApply;
    private String startTime;
    private String endTime;
    private String taskId;
    private List<SysUser> sysUsers;

    public LeaveApplyInfoVo() {
    }

    public LeaveApplyInfoVo(LeaveApply leaveApply, String startTime, String endTime, String taskId, List<SysUser> sysUsers) {
        this.leaveApply = leaveApply;
        this.startTime = startTime;
        this.endTime = endTime;
        this.taskId = taskId;
        this.sysUsers = sysUsers;
    }

    public LeaveApply getLeaveApply() {
        return leaveApply;
    }

    public void setLeaveApply(LeaveApply leaveApply) {
        this.leaveApply = leaveApply;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<SysUser> getSysUsers() {
        return sysUsers;
    }

    public void setSysUsers(List<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }
}
