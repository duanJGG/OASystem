package com.eoa.web.controller.eoa;

import com.eoa.common.annotation.Log;
import com.eoa.common.core.controller.BaseController;
import com.eoa.common.core.domain.AjaxResult;
import com.eoa.common.core.page.TableDataInfo;
import com.eoa.common.enums.BusinessType;
import com.eoa.common.validator.CreateGroup;
import com.eoa.daily.domain.Daily;
import com.eoa.daily.domain.vo.DailyVo;
import com.eoa.daily.flowable.AskForDailyService;
import com.eoa.daily.service.IDailyService;
import com.eoa.todo.domain.Todo;
import com.eoa.todo.domain.vo.TodoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eoa/daily")
public class DailyController extends BaseController {

    @Autowired
    private IDailyService dailyService;

    @Autowired
    AskForDailyService askForDailyService;

    /**
     * 新增日报
     */
    @PreAuthorize("hasPermission('check:daily:add')")
    @Log(title = "新增日报", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody DailyVo dailyVo) {
        return dailyService.insertDaily(dailyVo);
    }

    /**
     * 查看当前用户所有待审日报
     * @param
     * @return
     */
    @PreAuthorize("hasPermission('check:daily:list')")
    @GetMapping("/audit/list")
    public TableDataInfo auditList() {
        startPage();
        List<Daily> list = dailyService.selectAuditList();
        return getDataTable(list);
    }

    /**
     * 获取该用户需要审批的日报
     * @param
     * @return
     */
    @PreAuthorize("hasPermission('check:daily:list')")
    @GetMapping("/audit/daily")
    public TableDataInfo auditDaily() {
        startPage();
        List<Daily> list = dailyService.selectAuditDailyAuditList();
        return getDataTable(list);
    }

    /**
     * 审批任务
     */
    @PreAuthorize("hasPermission('check:daily:approve')")
    @PutMapping("/approve")
    public AjaxResult approveDilay(@RequestBody DailyVo dailyVo) {
        return dailyService.approveDilay(dailyVo);
    }

    /**
     * 查看历史日报
     * @param
     * @return
     */
    @PreAuthorize("hasPermission('check:daily:list')")
    @GetMapping("/history/list")
    public TableDataInfo historyList() {
        startPage();
        List<Daily> list = dailyService.selectHistoryList();
        return getDataTable(list);
    }

    /**
     * 查看处理的审批
     * @param
     * @return
     */
    @PreAuthorize("hasPermission('check:daily:approve')")
    @GetMapping("/history/approve")
    public TableDataInfo historyApproveList() {
        startPage();
        List<Daily> list = dailyService.historyApproveList();
        return getDataTable(list);
    }
}
