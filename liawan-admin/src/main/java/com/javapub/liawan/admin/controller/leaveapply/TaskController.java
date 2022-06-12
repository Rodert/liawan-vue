package com.javapub.liawan.admin.controller.leaveapply;

import com.javapub.liawan.common.core.controller.BaseController;
import com.javapub.liawan.common.core.domain.AjaxResult;
import com.javapub.liawan.common.core.domain.entity.SysUser;
import com.javapub.liawan.common.core.domain.model.LoginUser;
import com.javapub.liawan.common.core.page.TableDataInfo;
import com.javapub.liawan.common.utils.StringUtils;
import com.javapub.liawan.system.domain.TaskInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.engine.FormService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/6/11 20:53
 * @Version: 1.0
 * @Description:
 */

@Api(value = "待办任务接口")
@Controller
@RequestMapping("/task/manage")
public class TaskController extends BaseController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    FormService formService;

    private String prefix = "flowable/task";

    @GetMapping("/mytask")
    public String mytasks() {
        return prefix + "/mytasks";
    }

    @GetMapping("/alltasks")
    public String alltasks() {
        return prefix + "/alltasks";
    }

    /**
     * 查询我的待办任务列表
     */
    @ApiOperation("查询我的待办任务列表")
    @PostMapping("/mylist")
    @ResponseBody
    public TableDataInfo mylist(TaskInfo param) {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        String username = user.getUserName();
        TaskQuery condition = taskService.createTaskQuery().taskAssignee(username);
        if (StringUtils.isNotEmpty(param.getTaskName())) {
            condition.taskName(param.getTaskName());
        }
        if (StringUtils.isNotEmpty(param.getProcessName())) {
            condition.processDefinitionName(param.getProcessName());
        }
        int total = condition.active().orderByTaskCreateTime().desc().list().size();
        int start = (param.getPageNum() - 1) * param.getPageSize();
        List<Task> taskList = condition.active().orderByTaskCreateTime().desc().listPage(start, param.getPageSize());
        List<TaskInfo> tasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskList.stream().forEach(a -> {
            ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(a.getProcessInstanceId()).singleResult();
            TaskInfo info = new TaskInfo();
            info.setAssignee(a.getAssignee());
            info.setBusinessKey(process.getBusinessKey());
            info.setCreateTime(sdf.format(a.getCreateTime()));
            info.setTaskName(a.getName());
            info.setExecutionId(a.getExecutionId());
            info.setProcessInstanceId(a.getProcessInstanceId());
            info.setProcessName(process.getProcessDefinitionName());
            info.setStarter(process.getStartUserId());
            info.setStartTime(sdf.format(process.getStartTime()));
            info.setTaskId(a.getId());
            String formKey = formService.getTaskFormData(a.getId()).getFormKey();
            info.setFormKey(formKey);
            tasks.add(info);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(tasks);
        rspData.setTotal(total);
        return rspData;
    }

    /**
     * 查询所有待办任务列表
     */
    @ApiOperation("查询所有待办任务列表")
    @PostMapping("/alllist")
    @ResponseBody
    public TableDataInfo alllist(TaskInfo param) {
        TaskQuery condition = taskService.createTaskQuery();
        if (StringUtils.isNotEmpty(param.getTaskName())) {
            condition.taskName(param.getTaskName());
        }
        if (StringUtils.isNotEmpty(param.getProcessName())) {
            condition.processDefinitionName(param.getProcessName());
        }
        int total = condition.active().orderByTaskCreateTime().desc().list().size();
        int start = (param.getPageNum() - 1) * param.getPageSize();
        List<Task> taskList = condition.active().orderByTaskCreateTime().desc().listPage(start, param.getPageSize());
        List<TaskInfo> tasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskList.stream().forEach(a -> {
            ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(a.getProcessInstanceId()).singleResult();
            TaskInfo info = new TaskInfo();
            info.setAssignee(a.getAssignee());
            info.setBusinessKey(process.getBusinessKey());
            info.setCreateTime(sdf.format(a.getCreateTime()));
            info.setTaskName(a.getName());
            info.setExecutionId(a.getExecutionId());
            info.setProcessInstanceId(a.getProcessInstanceId());
            info.setProcessName(process.getProcessDefinitionName());
            info.setStarter(process.getStartUserId());
            info.setStartTime(sdf.format(process.getStartTime()));
            info.setTaskId(a.getId());
            String formKey = formService.getTaskFormData(a.getId()).getFormKey();
            info.setFormKey(formKey);
            tasks.add(info);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(tasks);
        rspData.setTotal(total);
        return rspData;
    }

    /**
     * 用taskid查询formkey
     **/
    @ApiOperation("用taskid查询formkey")
    @PostMapping("/forminfo/{taskId}")
    @ResponseBody
    public AjaxResult alllist(@PathVariable String taskId) {
        String formKey = formService.getTaskFormData(taskId).getFormKey();
        return AjaxResult.success(formKey);
    }

    @ApiOperation("办理一个用户任务")
    @RequestMapping(value = "/completeTask/{taskId}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult completeTask(@PathVariable("taskId") String taskId, @RequestParam(required = false) Map<String, Object> variables) {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        String username = user.getUserName();
        taskService.setAssignee(taskId, username);
        if (variables == null) {
            taskService.complete(taskId);
        } else {
            taskService.complete(taskId, variables);
        }
        return AjaxResult.success();
    }
}
