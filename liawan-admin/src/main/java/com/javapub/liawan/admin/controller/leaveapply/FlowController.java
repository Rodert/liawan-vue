package com.javapub.liawan.admin.controller.leaveapply;


import com.javapub.liawan.common.core.controller.BaseController;
import com.javapub.liawan.common.core.domain.AjaxResult;
import com.javapub.liawan.common.core.page.TableDataInfo;
import com.javapub.liawan.common.utils.StringUtils;
import com.javapub.liawan.system.domain.leaveapply.Process;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/6/14 20:54
 * @Version: 1.0
 * @Description: 流程管理
 */

@Api(value = "部署管理接口")
@Controller
@RequestMapping("/flow/manage")
public class FlowController extends BaseController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ProcessEngineConfiguration configuration;

    private String prefix = "flowable/manage";

    @GetMapping("")
    public String processList() {
        return prefix + "/processList";
    }

    @GetMapping("deploy")
    public String deploy() {
        return prefix + "/deployProcess";
    }

    @ApiOperation("上传一个工作流文件")
    @RequestMapping(value = "/uploadworkflow", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult fileupload(@RequestParam MultipartFile uploadfile) {
        try {
            String filename = uploadfile.getOriginalFilename();
            InputStream is = uploadfile.getInputStream();
            if (filename.endsWith("zip")) {
                repositoryService.createDeployment().name(filename).addZipInputStream(new ZipInputStream(is)).deploy();
            } else if (filename.endsWith("bpmn") || filename.endsWith("xml")) {
                repositoryService.createDeployment().name(filename).addInputStream(filename, is).deploy();
            } else {
                return AjaxResult.error("文件格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("部署失败");
        }
        return AjaxResult.success("部署成功");
    }

    @ApiOperation("查询已部署工作流列表")
    @RequestMapping(value = "/getprocesslists", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo getlist(@RequestParam(required = false) String key, @RequestParam(required = false) String name) {
        startPage();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotEmpty(key)) {
            query.processDefinitionKey(key);
        }
        if (StringUtils.isNotEmpty(name)) {
            query.processDefinitionName(name);
        }
        List<ProcessDefinition> list = query.list();
        TableDataInfo page = getDataTable(list);
        List<Process> mylist = new ArrayList<Process>();
        List<ProcessDefinition> pageList = (List<ProcessDefinition>) page.getRows();
        for (int i = 0; i < pageList.size(); i++) {
            Process p = new Process();
            p.setDeploymentId(pageList.get(i).getDeploymentId());
            p.setId(pageList.get(i).getId());
            p.setKey(pageList.get(i).getKey());
            p.setName(pageList.get(i).getName());
            p.setResourceName(pageList.get(i).getResourceName());
            p.setDiagramresourceName(pageList.get(i).getDiagramResourceName());
            mylist.add(p);
        }
        return getDataTable(mylist);
    }

    @ApiOperation("删除一次部署的工作流")
    @RequestMapping(value = "/remove/{deploymentId}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult remove(@PathVariable String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
        return AjaxResult.success();
    }


    @ApiOperation("查看工作流图片")
    @RequestMapping(value = "/showresource", method = RequestMethod.GET)
    public void showresource(@RequestParam("pdid") String pdid,
                             HttpServletResponse response) throws Exception {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pdid);
        ProcessDiagramGenerator diagramGenerator = configuration.getProcessDiagramGenerator();
        InputStream is = diagramGenerator.generateDiagram(bpmnModel, "png", "宋体", "宋体", "宋体", configuration.getClassLoader(), true);
        ServletOutputStream output = response.getOutputStream();
        IOUtils.copy(is, output);
    }

    @ApiOperation("查看工作流定义")
    @RequestMapping(value = "/showProcessDefinition/{pdid}/{resource}", method = RequestMethod.GET)
    public void showProcessDefinition(@PathVariable("pdid") String pdid, @PathVariable(value = "resource") String resource,
                                      HttpServletResponse response) throws Exception {
        InputStream is = repositoryService.getResourceAsStream(pdid, resource);
        ServletOutputStream output = response.getOutputStream();
        IOUtils.copy(is, output);
    }

}
