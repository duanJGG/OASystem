package com.eoa.leave.service.impl;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.common.core.domain.entity.SysUser;
import com.eoa.common.utils.SecurityUtils;
import com.eoa.common.utils.StringUtils;
import com.eoa.common.utils.bean.BeanUtils;
import com.eoa.leave.domain.Leave;
import com.eoa.leave.domain.vo.LeaveVo;
import com.eoa.leave.mapper.LeaveMapper;
import com.eoa.leave.service.ILeaveService;
import com.eoa.system.service.ISysUserService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ILeaveServiceImpl implements ILeaveService {

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    ISysUserService userService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Override
    @Transactional
    public AjaxResult insertLeave(LeaveVo leaveVo) {
        Leave leave = new Leave();
        Long userId = SecurityUtils.getUserId();
        leave.setUserId(userId);
        SysUser sysUser = userService.selectUserById(userId);
        if (leaveVo.getLeaveId()!=null && leaveVo.getStatus() ==3){
            BeanUtils.copyProperties(leaveVo,leave);
            Duration duration = Duration.between(leave.getBeginTime(), leave.getEndTime());
            leave.setLeaveDays(duration.toDays());
            leave.setLeaveHours(duration.toHours());
            leave.setStatus(2);
            leave.setReason("");
            leave.setAuditName("");
            try {
                HashMap<String, Object> vars = new HashMap<>();
                //申请人
                //vars.put("oneself", sysUser.getUserName());
                if (leave.getLeaveDays() <= 3) {
                    //审核人
                    vars.put("f_director", sysUser.getSuperior());
                } else {
                    //审核人
                    vars.put("z_director", sysUser.getSuperior());
                }
                //请假天数
                vars.put("days", leave.getLeaveDays());
                //vars.put("leaveId", leave.getLeaveId());
//                runtimeService.startProcessInstanceByKey("leave_process", vars);
                //这里有错误
//                Task task = taskService.createTaskQuery()
//                        .taskAssignee(SecurityUtils.getUsername()).singleResult();
                leaveMapper.updateLeaveInfo(leave);
                taskService.complete(leaveVo.getTaskId(),vars);
                return AjaxResult.success("请假提交成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return AjaxResult.success("请假提交失败");
        }
        BeanUtils.copyProperties(leaveVo, leave);
        leave.setStatus(2);
        leave.setLeaveName(sysUser.getNickName());
        Duration duration = Duration.between(leave.getBeginTime(), leave.getEndTime());
        leave.setLeaveDays(duration.toDays());
        leave.setLeaveHours(duration.toHours());
        leave.setCreateTime(LocalDateTime.now());
        leave.setDelFlag(0);
        if (leaveMapper.insertLeave(leave) > 0) {
            try {
                HashMap<String, Object> vars = new HashMap<>();
                //申请人
                vars.put("oneself", sysUser.getUserName());
                if (leave.getLeaveDays() <= 3) {
                    //审核人
                    vars.put("f_director", sysUser.getSuperior());
                } else {
                    //审核人
                    vars.put("z_director", sysUser.getSuperior());
                }
                //请假天数
                vars.put("days", leave.getLeaveDays());
                vars.put("leaveId", leave.getLeaveId());
                ProcessInstance leave_process = runtimeService.startProcessInstanceByKey("leave_process", vars);
                //
                Task task = taskService.createTaskQuery().processInstanceId(leave_process.getId()).singleResult();
                taskService.complete(task.getId());
                return AjaxResult.success("请假提交成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.error("请假提交失败");
    }

    /**
     * 用户查看待审批请假
     */
    @Override
    public List<Leave> selectWaitList() {
        List<Execution> executions = runtimeService.createExecutionQuery()
                .startedBy(SecurityUtils.getUsername()).list();
        ArrayList<Leave> leaves = new ArrayList<>();
        for (Execution execution : executions) {
            String executionId = execution.getId();
            Long leaveId = (Long) runtimeService.getVariable(executionId, "leaveId");
            if (leaveId==null){
                continue;
            }
            leaves.add(leaveMapper.selectWaitStatusList(leaveId));
        }
        return leaves;
    }

    /**
     * 用户查看已经审批的请假
     */
    @Override
    public List<Leave> selectHistoryList() {
        Leave leave = new Leave();
        leave.setUserId(SecurityUtils.getUserId());
        leave.setStatus(2);
        return leaveMapper.selectHistoryList(leave);
    }

    /**
     * 上级查看需要处理的请假
     */
    @Override
    public List<Leave> waitApproveList() {
        ArrayList<Leave> leaves = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateUser(SecurityUtils.getUserId() + "").list();
        for (Task task : tasks) {
            Long leaveId = (Long) taskService.getVariable(task.getId(), "leaveId");
            if (leaveId==null){
                continue;
            }
            Leave leave = leaveMapper.selectWaitList(leaveId);
            leave.setTaskId(task.getId());
            leaves.add(leave);
        }
        return leaves;
    }

    /**
     * 上级查看已经处理的请假
     */
    @Override
    public List<Leave> historyApproveList() {
        Long auditId = SecurityUtils.getUserId();
        return leaveMapper.historyApproveList(auditId);
    }

    /**
     * 上级处理请假
     *
     * @param leaveVo
     * @return
     */
    @Override
    @Transactional
    public AjaxResult approveLeave(LeaveVo leaveVo) {
        HashMap<String, Object> vars = new HashMap<>();
        Integer status = leaveVo.getStatus();
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = userService.selectUserById(userId);
        Long days = (Long) taskService.getVariable(leaveVo.getTaskId(), "days");
        Long leaveId = (Long) taskService.getVariable(leaveVo.getTaskId(), "leaveId");
        Boolean flag = (Boolean) taskService.getVariable(leaveVo.getTaskId(), "z_approval");
        if (flag == null) {
            flag = false;
        }
        Leave leave = new Leave();
        leave.setLeaveId(leaveId);
        leave.setAuditId(userId);
        leave.setAuditName(sysUser.getNickName());
        if (flag) {
            if (status == 0) {
                vars.put("ceo_reject", true);
                vars.put("ceo_approval", true);
                leave.setStatus(0);
            } else if (status == 3) {
                vars.put("ceo_approval", false);
                if (!StringUtils.isEmpty(leaveVo.getReason())) {
                    vars.put("reason", leaveVo.getReason());
                }
                leave.setStatus(3);
                leave.setReason(leave.getReason());
            } else if (status == 1) {
                vars.put("ceo_approval", true);
                leave.setStatus(1);
            }
        }
        if (days <= 3 && !flag) {
            if (status == 0) {
                vars.put("fu_reject", true);
                vars.put("fu_approval", true);
                leave.setStatus(0);

            } else if (status == 3) {
                vars.put("fu_approval", false);
                if (!StringUtils.isEmpty(leaveVo.getReason())) {
                    vars.put("reason", leaveVo.getReason());
                }
                leave.setStatus(3);
                leave.setReason(leaveVo.getReason());
            } else if (status == 1) {
                vars.put("fu_approval", true);
                leave.setStatus(1);
            }
        }
        else if (days > 3 && !flag){
            if (status == 0) {
                vars.put("z_reject", true);
                vars.put("z_approval", true);
                leave.setStatus(0);
            } else if (status == 3) {
                vars.put("z_approval", false);
                if (!StringUtils.isEmpty(leaveVo.getReason())) {
                    vars.put("reason", leaveVo.getReason());
                }
                leave.setStatus(3);
                leave.setReason(leave.getReason());
            } else if (status == 1) {
                vars.put("z_approval", true);
                vars.put("ceo", sysUser.getSuperior());
                leave.setStatus(1);
            }
        }
        try {
            taskService.complete(leaveVo.getTaskId(), vars);
            leaveMapper.updateLeaveStatus(leave);
            return AjaxResult.success("审批成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("审批失败");
    }

    /***
     * 被驳回的请假
     * @return
     */
    @Override
    public List<Leave> rejectedList() {
        Leave leave = new Leave();
        leave.setUserId(SecurityUtils.getUserId());
        leave.setStatus(3);
        List<Leave> leaves = leaveMapper.rejectedList(leave);
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(SecurityUtils.getUsername()).list();
        for (Task task : tasks) {
            Long leaveId = (Long) taskService.getVariable(task.getId(), "leaveId");
            if (leaveId==null){
                continue;
            }
            for (Leave leaf : leaves) {
                if (leaf.getLeaveId().equals(leaveId)){
                    leaf.setTaskId(task.getId());
                }
            }
        }
        return leaves;
    }
}
