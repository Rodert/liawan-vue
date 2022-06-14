package com.javapub.liawan.system.domain.leaveapply;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/6/14 16:23
 * @Version: 1.0
 * @Description: 流程实例信息
 */

public class FlowInfo {

    String processInstanceId;

    String businessKey;

    String name;

    Boolean suspended;

    Boolean ended;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime;

    String startUserId;
    // 当前节点
    String currentTask;
    // 当前办理人
    String assignee;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public Boolean getEnded() {
        return ended;
    }

    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    public String getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(String currentTask) {
        this.currentTask = currentTask;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
