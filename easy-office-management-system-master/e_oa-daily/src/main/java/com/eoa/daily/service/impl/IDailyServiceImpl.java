package com.eoa.daily.service.impl;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.common.core.domain.entity.SysUser;
import com.eoa.common.utils.SecurityUtils;
import com.eoa.common.utils.StringUtils;
import com.eoa.daily.domain.Daily;
import com.eoa.daily.domain.vo.DailyVo;
import com.eoa.daily.mapper.DailyMapper;
import com.eoa.daily.service.IDailyService;
import com.eoa.system.service.ISysUserService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.task.api.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("iDailyServiceImpl")
public class IDailyServiceImpl implements IDailyService {

    @Autowired
    DailyMapper dailyMapper;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    ISysUserService userService;

    @Autowired
    HistoryService historyService;

    @Override
    @Transactional
    public AjaxResult insertDaily(DailyVo dailyVo) {
        Daily daily = new Daily();
        BeanUtils.copyProperties(dailyVo, daily);
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = userService.selectUserById(userId);
        daily.setUserName(sysUser.getNickName());
        daily.setUserId(userId);
        daily.setStatus(2);
        daily.setCreateTime(LocalDateTime.now());
        daily.setDelFlag(0);
        if (dailyMapper.insertDaily(daily) > 0){
            try {
                HashMap<String, Object> vars = new HashMap<>();
                //申请人
                vars.put("applicant", sysUser.getNickName());
                //审核人
                vars.put("approveUsers", sysUser.getSuperior());
                vars.put("title", dailyVo.getTitle());
                vars.put("content", dailyVo.getContent());
                //日报id
                vars.put("dailyId",daily.getDailyId());
                runtimeService.startProcessInstanceByKey("daily_audit", vars);
                return AjaxResult.success("日报提交成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.error("日报提交失败");
    }

    @Override
    public int updateDailyStatus(Daily daily) {
        return dailyMapper.updateDailyStatus(daily);
    }

    /**
     * 查看当前用户所有待审日报
     * @return
     */
    @Override
    public List<Daily> selectAuditList() {
        List<Execution> executions = runtimeService.createExecutionQuery()
                .startedBy(SecurityUtils.getUsername()).list();
        List<Daily> dailies = new ArrayList<>();
        for (Execution execution : executions) {
            //获取每个日报流程的id
            String executionId = execution.getId();
            Long dailyId = (Long) runtimeService.getVariable(execution.getId(), "dailyId");
            if(dailyId==null){
                continue;
            }
//            String title = (String) runtimeService.getVariable(execution.getId(), "title");
//            String content = (String) runtimeService.getVariable(execution.getId(), "content");
//            daily.setTitle(title);
//            daily.setContent(content);
            dailies.add(dailyMapper.selectAuditList(dailyId));
        }
        return dailies;
//        Daily daily = new Daily();
//        daily.setUserId(SecurityUtils.getUserId());
//        daily.setStatus(2);
//        return dailyMapper.selectAuditList(daily);
    }

    @Override
    public List<Daily> selectAuditDailyAuditList() {
        ArrayList<Daily> dailys = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(SecurityUtils.getUserId() + "").list();
        if (tasks==null || tasks.size()==0){
            return dailys;
        }
        for (Task task : tasks) {
            Long dailyId = (Long) taskService.getVariable(task.getId(), "dailyId");
            if (dailyId==null){
                continue;
            }
            //申请人
            String applicant = (String) taskService.getVariable(task.getId(), "applicant");
            Daily daily = dailyMapper.selectAuditList(dailyId);
            daily.setTaskId(task.getId());
            daily.setUserName(applicant);
            dailys.add(daily);
        }
        return dailys;
    }

    @Override
    @Transactional
    public AjaxResult approveDilay(DailyVo dailyVo) {
        try {
            HashMap<String, Object> vars = new HashMap<>();
            Integer status = dailyVo.getStatus();
            boolean flag = true;
            if (status == 0) {
                flag = false;
            }
            if (!StringUtils.isEmpty(dailyVo.getReason())) {
                vars.put("reason", dailyVo.getReason());
            }
            vars.put("approval", flag);
            taskService.complete(dailyVo.getTaskId(), vars);
            return AjaxResult.success("审批成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("审批失败");
    }

    @Override
    public List<Daily> selectHistoryList() {
        Daily daily = new Daily();
        daily.setUserId(SecurityUtils.getUserId());
        daily.setStatus(2);
        return dailyMapper.selectResultList(daily);
    }

    @Override
    public List<Daily> historyApproveList() {
        Long auditId = SecurityUtils.getUserId();
        return dailyMapper.historyApproveList(auditId);
    }
}
