package com.javapub.liawan.admin.controller.leaveapply;

import com.javapub.liawan.common.annotation.Log;
import com.javapub.liawan.common.core.controller.BaseController;
import com.javapub.liawan.common.core.domain.AjaxResult;
import com.javapub.liawan.common.core.domain.entity.SysUser;
import com.javapub.liawan.common.core.page.TableDataInfo;
import com.javapub.liawan.common.enums.BusinessType;
import com.javapub.liawan.common.utils.poi.ExcelUtil;
import com.javapub.liawan.system.domain.LeaveApply;
import com.javapub.liawan.system.domain.vo.LeaveApplyInfoVo;
import com.javapub.liawan.system.service.ILeaveApplyService;
import com.javapub.liawan.system.service.ISysUserService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 请假Controller
 *
 * @author
 * @date 2022-06-10
 */
@RestController
@RequestMapping("/system/apply")
public class LeaveApplyController extends BaseController {

    @Autowired
    private ILeaveApplyService leaveApplyService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询请假列表
     */
    @PreAuthorize("@ss.hasPermi('system:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(LeaveApply leaveApply) {
        startPage();
        List<LeaveApply> list = leaveApplyService.selectLeaveApplyList(leaveApply);
        return getDataTable(list);
    }

    /**
     * 导出请假列表
     */
    @PreAuthorize("@ss.hasPermi('system:apply:export')")
    @Log(title = "请假", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LeaveApply leaveApply) {
        List<LeaveApply> list = leaveApplyService.selectLeaveApplyList(leaveApply);
        ExcelUtil<LeaveApply> util = new ExcelUtil<LeaveApply>(LeaveApply.class);
        util.exportExcel(response, list, "请假数据");
    }

    /**
     * 获取请假详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:apply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(leaveApplyService.selectLeaveApplyById(id));
    }
//
//    /**
//     * 新增请假
//     */
//    @GetMapping("/add")
//    public List<SysUser> add() {
////        SysUser user = SecurityUtils.getLoginUser().getUser();
//        return userService.selectUserList(new SysUser());
//    }

    /**
     * 发起请假
     */
    @PreAuthorize("@ss.hasPermi('system:apply:add')")
    @Log(title = "请假", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LeaveApply leaveApply) {
        leaveApply.setApplyTime(new Date());
        return toAjax(leaveApplyService.insertLeaveApply(leaveApply));
    }

    /**
     * 修改请假
     */
    @PreAuthorize("@ss.hasPermi('system:apply:edit')")
    @Log(title = "请假", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LeaveApply leaveApply) {
        return toAjax(leaveApplyService.updateLeaveApply(leaveApply));
    }

    /**
     * 删除请假
     */
    @PreAuthorize("@ss.hasPermi('system:apply:remove')")
    @Log(title = "请假", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(leaveApplyService.deleteLeaveApplyByIds(ids));
    }

    /**
     * 通过taskId获取任务信息
     *
     * @return
     */
    @GetMapping("/getLeavepplyInfo/{taskId}")
    public LeaveApplyInfoVo deptleadercheck(@PathVariable String taskId) {
        Task t = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = t.getProcessInstanceId();
        ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (p != null) {
            LeaveApply leaveApply = leaveApplyService.selectLeaveApplyById(Long.parseLong(p.getBusinessKey()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LeaveApplyInfoVo leaveApplyInfoVo = new LeaveApplyInfoVo(
                    leaveApply,
                    sdf.format(leaveApply.getStartTime()),
                    sdf.format(leaveApply.getEndTime()),
                    taskId,
                    userService.selectUserList(new SysUser()));
            return leaveApplyInfoVo;
        }
        return new LeaveApplyInfoVo();
    }

}



