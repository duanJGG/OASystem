package com.eoa.daily.flowable;

import com.eoa.common.core.domain.entity.SysUser;
import com.eoa.common.utils.SecurityUtils;
import com.eoa.daily.domain.vo.DailyVo;
import com.eoa.daily.service.IDailyService;
import com.eoa.system.service.ISysUserService;
import org.apache.commons.collections4.Put;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AskForDailyService {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    private IDailyService dailyService;

    //由于涉及到多张表，加个事物 确保同事成功或者同时失败
    public boolean askForDaily(DailyVo dailyVo){
        try {
            HashMap<String, Object> vars = new HashMap<>();
            Long userId = SecurityUtils.getUserId();
            //申请人
            vars.put("applicant", SecurityUtils.getUsername());
            SysUser sysUser = sysUserService.selectUserById(userId);
            //审核人
            vars.put("approveUsers",sysUser.getSuperior());
            vars.put("title",dailyVo.getTitle());
            vars.put("content",dailyVo.getContent());
            //日报id
            runtimeService.startProcessInstanceByKey("daily_audit",vars);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
