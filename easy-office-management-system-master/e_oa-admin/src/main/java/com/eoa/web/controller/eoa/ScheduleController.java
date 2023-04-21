package com.eoa.web.controller.eoa;

import com.eoa.common.annotation.Log;
import com.eoa.common.core.controller.BaseController;
import com.eoa.common.core.domain.AjaxResult;
import com.eoa.common.core.page.TableDataInfo;
import com.eoa.common.enums.BusinessType;
import com.eoa.common.validator.CreateGroup;
import com.eoa.common.validator.EditGroup;
import com.eoa.common.validator.EditStatusGroup;
import com.eoa.schedule.domain.vo.ScheduleVo;
import com.eoa.schedule.service.IScheduleService;
import com.eoa.schedule.domain.Schedule;
import com.eoa.schedule.domain.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eoa/schedule")
public class ScheduleController extends BaseController {

    @Autowired
    private IScheduleService scheduleService;

    /**
     * 新增日程
     */
    @PreAuthorize("hasPermission('eoa:schedule:add')")
    @Log(title = "新增日程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody ScheduleVo scheduleVo) {
        return scheduleService.insertSchedule(scheduleVo);
    }

    /**
     * 查询日程列表
     */
    @PreAuthorize("hasPermission('eoa:schedule:list')")
    @GetMapping("/list")
    public TableDataInfo list(Schedule schedule) {
        startPage();
        List<ScheduleVo> list = scheduleService.selectScheduleList(schedule);
        return getDataTable(list);
    }

    /**
     * 获取日程详细信息
     */
    @PreAuthorize("hasPermission('eoa:schedule:query')")
    @GetMapping(value = "/{scheduleId}")
    public AjaxResult getInfo(@PathVariable("scheduleId") Long scheduleId) {
        return success(scheduleService.selectScheduleById(scheduleId));
    }

    /**
     * 修改日程
     */
    @PreAuthorize("hasPermission('eoa:schedule:edit')")
    @Log(title = "修改日程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody ScheduleVo scheduleVo) {
        return scheduleService.updateSchedule(scheduleVo);
    }

    /**
     * 修改日程
     */
    @PreAuthorize("hasPermission('eoa:schedule:edit')")
    @Log(title = "修改日程状态", businessType = BusinessType.UPDATE)
    @PutMapping("status/{scheduleId}")
    public AjaxResult editStatus(@Validated(EditStatusGroup.class) @PathVariable("scheduleId") Long scheduleId) {
        return scheduleService.editStatus(scheduleId);
    }
    /**
     * 删除日程
     */
    @PreAuthorize("hasPermission('eoa:schedule:remove')")
    @Log(title = "删除日程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{scheduleIds}")
    public AjaxResult remove(@PathVariable Long[] scheduleIds) {
        return toAjax(scheduleService.deleteScheduleByIds(scheduleIds));
    }
}
