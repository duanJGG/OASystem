package com.eoa.flowable.daily;

import com.eoa.common.core.domain.entity.SysUser;
import com.eoa.common.utils.SecurityUtils;
import com.eoa.common.utils.spring.SpringUtils;
import com.eoa.daily.domain.Daily;
import com.eoa.daily.service.IDailyService;
import com.eoa.system.service.ISysUserService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 发送日报驳回消息
 */
public class DailyRejectServiceTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Map<String, Object> variables = delegateExecution.getVariables();
        Daily daily = new Daily();
        daily.setDailyId((Long)variables.get("dailyId"));
        daily.setStatus(0);
        Long userId = SecurityUtils.getUserId();
        ISysUserService userService = SpringUtils.getBean("sysUserServiceImpl");
        SysUser sysUser = userService.selectUserById(userId);
        daily.setAuditId(userId);
        daily.setAuditName(sysUser.getNickName());
        daily.setReason((String) variables.get("reason"));
        IDailyService dailyService = SpringUtils.getBean("iDailyServiceImpl");
        dailyService.updateDailyStatus(daily);
    }
}
