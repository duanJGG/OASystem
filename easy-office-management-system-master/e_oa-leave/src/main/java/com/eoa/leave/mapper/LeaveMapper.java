package com.eoa.leave.mapper;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.leave.domain.Leave;

import java.util.List;

public interface LeaveMapper {
    int insertLeave(Leave leave);

    int updateLeaveInfo(Leave leave);

    Leave selectWaitList(Long leaveId);

    List<Leave> selectHistoryList(Leave leave);

    List<Leave> historyApproveList(Long auditId);

    void updateLeaveStatus(Leave leave);

    List<Leave> rejectedList(Leave leave);

    Leave selectWaitStatusList(Long leaveId);
}
