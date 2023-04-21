package com.eoa.schedule.mapper;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.schedule.domain.Schedule;
import com.eoa.schedule.domain.vo.ScheduleVo;

import java.util.List;

public interface ScheduleMapper {
    int insertSchedule(Schedule schedule);

    List<ScheduleVo> selectScheduleList(Schedule schedule);

    ScheduleVo selectScheduleById(Long scheduleId);

    int updateSchedule(Schedule schedule);

    int editStatus(Long scheduleId);

    int deleteScheduleByIds(Long[] scheduleIds);
}
