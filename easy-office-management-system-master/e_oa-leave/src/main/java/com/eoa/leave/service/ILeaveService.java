package com.eoa.leave.service;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.leave.domain.Leave;
import com.eoa.leave.domain.vo.LeaveVo;

import java.util.List;

public interface ILeaveService {
    AjaxResult insertLeave(LeaveVo leaveVo);

    List<Leave> selectWaitList();

    List<Leave> selectHistoryList();

    List<Leave> waitApproveList();

    List<Leave> historyApproveList();

    AjaxResult approveLeave(LeaveVo leaveVo);

    List<Leave> rejectedList();
}
