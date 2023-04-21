package com.eoa.schedule.service.impl;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.common.utils.SecurityUtils;
import com.eoa.schedule.domain.Schedule;
import com.eoa.schedule.domain.vo.ScheduleVo;
import com.eoa.schedule.mapper.ScheduleMapper;
import com.eoa.schedule.service.IScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IScheduleServiceImpl implements IScheduleService {

    @Autowired
    ScheduleMapper scheduleMapper;

    @Override
    public AjaxResult insertSchedule(ScheduleVo scheduleVo) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleVo,schedule);
        schedule.setUserId(SecurityUtils.getUserId());
        schedule.setStatus(0);
        schedule.setDelFlag(0);
        return scheduleMapper.insertSchedule(schedule)>0 ? AjaxResult.success("添加成功"):AjaxResult.error("添加失败");
    }

    @Override
    public List<ScheduleVo> selectScheduleList(Schedule schedule) {
        schedule.setUserId(SecurityUtils.getUserId());
        return scheduleMapper.selectScheduleList(schedule);
    }

    @Override
    public ScheduleVo selectScheduleById(Long scheduleId) {
        return scheduleMapper.selectScheduleById(scheduleId);
    }

    @Override
    public AjaxResult updateSchedule(ScheduleVo scheduleVo) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleVo,schedule);
        return scheduleMapper.updateSchedule(schedule) > 0 ? AjaxResult.success("修改成功"):AjaxResult.error("修改失败");
    }

    @Override
    public AjaxResult editStatus(Long scheduleId) {
        return scheduleMapper.editStatus(scheduleId)>0 ? AjaxResult.success("操作成功"):AjaxResult.error("操作失败");
    }

    @Override
    public int deleteScheduleByIds(Long[] scheduleIds) {
        return scheduleMapper.deleteScheduleByIds(scheduleIds);
    }
}
