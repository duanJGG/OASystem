package com.eoa.daily.service;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.daily.domain.Daily;
import com.eoa.daily.domain.vo.DailyVo;

import java.util.List;

public interface IDailyService {
    AjaxResult insertDaily(DailyVo dailyVo);

    int updateDailyStatus(Daily daily);

    List<Daily> selectAuditList();

    List<Daily> selectAuditDailyAuditList();

    AjaxResult approveDilay(DailyVo dailyVo);

    List<Daily> selectHistoryList();

    List<Daily> historyApproveList();

}
