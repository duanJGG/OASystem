package com.eoa.web.controller.eoa;

import com.eoa.common.annotation.Log;
import com.eoa.common.core.controller.BaseController;
import com.eoa.common.core.domain.AjaxResult;
import com.eoa.common.core.page.TableDataInfo;
import com.eoa.common.enums.BusinessType;
import com.eoa.common.validator.CreateGroup;
import com.eoa.leave.domain.Leave;
import com.eoa.leave.domain.vo.LeaveVo;
import com.eoa.leave.service.ILeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eoa/leave")
public class LeaveController extends BaseController {

    @Autowired
    ILeaveService leaveService;

    /**
     * 新增请假
     */
    @PreAuthorize("hasPermission('flow:leave:add')")
    @Log(title = "新增请假", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody LeaveVo leaveVo) {
        return leaveService.insertLeave(leaveVo);
    }

    /**
     * 用户查看待审批请假
     */
    @PreAuthorize("hasPermission('flow:leave:list')")
    @GetMapping("/wait/list")
    public TableDataInfo waitList() {
        startPage();
        List<Leave> list = leaveService.selectWaitList();
        return getDataTable(list);
    }

    /**
     * 被驳回的请假
     */
    @PreAuthorize("hasPermission('flow:leave:list')")
    @GetMapping("/rejected/list")
    public TableDataInfo rejectedList() {
        startPage();
        List<Leave> list = leaveService.rejectedList();
        return getDataTable(list);
    }


    /**
     * 用户查看已经审批的请假
     */
    @PreAuthorize("hasPermission('flow:leave:list')")
    @GetMapping("/history/list")
    public TableDataInfo historyList() {
        startPage();
        List<Leave> list = leaveService.selectHistoryList();
        return getDataTable(list);
    }

    /**
     * 上级查看需要处理的请假
     */
    @PreAuthorize("hasPermission('flow:leave:approve')")
    @GetMapping("/wait/approve")
    public TableDataInfo waitApproveList() {
        startPage();
        List<Leave> list = leaveService.waitApproveList();
        return getDataTable(list);
    }

    /**
     * 上级处理请假
     */
    @PreAuthorize("hasPermission('flow:leave:approve')")
    @Log(title = "处理请假", businessType = BusinessType.UPDATE)
    @PutMapping("/approve")
    public AjaxResult approveLeave(@RequestBody LeaveVo leaveVo) {
        return leaveService.approveLeave(leaveVo);
    }


    /**
     * 上级查看已经处理的请假
     */
    @PreAuthorize("hasPermission('flow:leave:approve')")
    @GetMapping("/history/approve")
    public TableDataInfo historyApproveList() {
        startPage();
        List<Leave> list = leaveService.historyApproveList();
        return getDataTable(list);
    }
}
