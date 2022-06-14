package com.javapub.liawan.admin.controller.leaveapply;

import com.javapub.liawan.common.core.controller.BaseController;
import com.javapub.liawan.common.core.domain.AjaxResult;
import com.javapub.liawan.common.core.page.TableDataInfo;
import com.javapub.liawan.common.utils.StringUtils;
import com.javapub.liawan.system.domain.leaveapply.FlowInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/6/14 16:21
 * @Version: 1.0
 * @Description: 流程监控
 */

@Api(value = "流程监控接口")
@Controller
@RequestMapping("/flow/monitor")
public class FlowMonitorController extends BaseController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ProcessEngine processEngine;

    private String prefix = "flowable/monitor";

    @GetMapping("/instance")
    public String processList() {
        return prefix + "/processInstance";
    }

    @GetMapping("/history")
    public String processHistory() {
        return prefix + "/processHistory";
    }

    @GetMapping("/historyDetail")
    public String historyDetail(String processInstanceId, ModelMap mmap) {
        mmap.put("processInstanceId", processInstanceId);
        return prefix + "/processHistoryDetail";
    }

    @GetMapping("/processVariablesDetail")
    public String processVariablesDetail(String processInstanceId, ModelMap mmap) {
        mmap.put("processInstanceId", processInstanceId);
        return prefix + "/processVariablesDetail";
    }

    @ApiOperation("查询所有正在运行的流程实例列表")
    @RequestMapping(value = "/listProcess", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo getlist(@RequestParam(required = false) String bussinesskey, @RequestParam(required = false) String name,
                                 Integer pageSize, Integer pageNum) {
        int start = (pageNum - 1) * pageSize;
        ProcessInstanceQuery condition = runtimeService.createProcessInstanceQuery();
        if (StringUtils.isNotEmpty(bussinesskey)) {
            condition.processInstanceBusinessKey(bussinesskey);
        }
        if (StringUtils.isNotEmpty(name)) {
            condition.processDefinitionName(name);
        }
        List<ProcessInstance> processList = condition.orderByStartTime().desc().listPage(start, pageSize);
        int total = condition.orderByStartTime().desc().list().size();
        List<FlowInfo> flows = new ArrayList<>();
        processList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getProcessInstanceId());
            info.setBusinessKey(p.getBusinessKey());
            info.setName(p.getProcessDefinitionName());
            info.setStartTime(p.getStartTime());
            info.setStartUserId(p.getStartUserId());
            info.setSuspended(p.isSuspended());
            info.setEnded(p.isEnded());
            // 查看当前活动任务
            Task task = taskService.createTaskQuery().processInstanceId(p.getProcessInstanceId()).singleResult();
            info.setCurrentTask(task.getName());
            info.setAssignee(task.getAssignee());
            flows.add(info);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(flows);
        rspData.setTotal(total);
        return rspData;
    }

    @ApiOperation("查询所有流程实例列表-包含在运行和已结束")
    @RequestMapping(value = "/listHistoryProcess", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo listHistoryProcess(@RequestParam(required = false) String bussinesskey, @RequestParam(required = false) String name,
                                            Integer pageSize, Integer pageNum) {
        int total = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().desc().list().size();
        int start = (pageNum - 1) * pageSize;
        HistoricProcessInstanceQuery condition = historyService.createHistoricProcessInstanceQuery();
        if (StringUtils.isNotEmpty(bussinesskey)) {
            condition.processInstanceBusinessKey(bussinesskey);
        }
        if (StringUtils.isNotEmpty(name)) {
            condition.processDefinitionName(name);
        }
        List<HistoricProcessInstance> processList = condition.orderByProcessInstanceStartTime().desc().listPage(start, pageSize);
        List<FlowInfo> flows = new ArrayList<>();
        processList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getId());
            info.setBusinessKey(p.getBusinessKey());
            info.setName(p.getProcessDefinitionName());
            info.setStartTime(p.getStartTime());
            info.setEndTime(p.getEndTime());
            info.setStartUserId(p.getStartUserId());
            if (p.getEndTime() == null) {
                info.setEnded(false);
                // 查看当前活动任务
                Task task = taskService.createTaskQuery().processInstanceId(p.getId()).singleResult();
                info.setCurrentTask(task.getName());
                info.setAssignee(task.getAssignee());
            } else {
                info.setEnded(true);
            }
            flows.add(info);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(flows);
        rspData.setTotal(total);
        return rspData;
    }

    @ApiOperation("查询一个流程的活动历史")
    @RequestMapping(value = "/history/{processInstanceId}", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo history(@PathVariable String processInstanceId, Integer pageSize, Integer pageNum) {
        int start = (pageNum - 1) * pageSize;
        HashSet<String> set = new HashSet<>();
        set.add("userTask");
        List<HistoricActivityInstance> history = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityTypes(set).orderByHistoricActivityInstanceStartTime().asc().listPage(start, pageSize);
        int total = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityTypes(set).orderByHistoricActivityInstanceStartTime().asc().list().size();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(history);
        rspData.setTotal(total);
        return rspData;
    }


    @ApiOperation("查询所有正在运行的执行实例列表")
    @RequestMapping(value = "/listExecutions", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo listExecutions(@RequestParam(required = false) String key, @RequestParam(required = false) String name,
                                        Integer pageSize, Integer pageNum) {
        int start = (pageNum - 1) * pageSize;
        List<Execution> executionList = runtimeService.createExecutionQuery().orderByProcessInstanceId().desc().listPage(start, pageSize);
        int total = runtimeService.createExecutionQuery().orderByProcessInstanceId().desc().list().size();
        List<FlowInfo> flows = new ArrayList<>();
        executionList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getProcessInstanceId());
            info.setSuspended(p.isSuspended());
            info.setEnded(p.isEnded());
            flows.add(info);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(flows);
        rspData.setTotal(total);
        return rspData;
    }

    @ApiOperation("流程图进度追踪")
    @RequestMapping(value = {"/traceProcess/{processInstanceId}"}, method = RequestMethod.GET)
    public void traceprocess(@PathVariable String processInstanceId, HttpServletResponse response) {
        // 根据流程实例ID获得当前处于活动状态的ActivityId合集
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = pi.getProcessDefinitionId();
        List<String> highLightedActivities = new ArrayList<>();

        // 获得活动的节点
        List<HistoricActivityInstance> highLightedActivityList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        List<String> highLightedFlows = new ArrayList<>();

        for (HistoricActivityInstance tempActivity : highLightedActivityList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivities.add(activityId);
            if ("sequenceFlow".equals(tempActivity.getActivityType())) {
                highLightedFlows.add(tempActivity.getActivityId());
            }
        }

        // 获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration engConf = processEngine.getProcessEngineConfiguration();

        ProcessDiagramGenerator diagramGenerator = engConf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "bmp", highLightedActivities, highLightedFlows, "宋体",
                "宋体", "宋体", engConf.getClassLoader(), 1.0, true);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int length;
        try {
            out = response.getOutputStream();
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.closeSilently(out);
            IoUtil.closeSilently(in);
        }
    }

    @ApiOperation("挂起一个流程实例")
    @RequestMapping(value = "/suspend/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult suspend(@PathVariable String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
        return AjaxResult.success();
    }

    @ApiOperation("唤醒一个挂起的流程实例")
    @RequestMapping(value = "/run/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult rerun(@PathVariable String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
        return AjaxResult.success();
    }

    @ApiOperation("查询一个流程的变量")
    @RequestMapping(value = "/variables/{processInstanceId}", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo variables(@PathVariable String processInstanceId, Integer pageSize, Integer pageNum) {
        int start = (pageNum - 1) * pageSize;
        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).orderByVariableName().asc().listPage(start, pageSize);
        int total = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).orderByVariableName().asc().list().size();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(variables);
        rspData.setTotal(total);
        return rspData;
    }
}
