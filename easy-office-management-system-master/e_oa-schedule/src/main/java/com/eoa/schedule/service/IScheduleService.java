package com.eoa.schedule.service;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.schedule.domain.Schedule;
import com.eoa.schedule.domain.vo.ScheduleVo;

import java.util.List;

public interface IScheduleService {
    AjaxResult insertSchedule(ScheduleVo scheduleVo);

    List<ScheduleVo> selectScheduleList(Schedule schedule);

    ScheduleVo selectScheduleById(Long scheduleId);

    AjaxResult updateSchedule(ScheduleVo scheduleVo);

    AjaxResult editStatus(Long scheduleId);

    int deleteScheduleByIds(Long[] scheduleIds);
}
