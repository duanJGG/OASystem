package com.eoa.daily.mapper;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.daily.domain.Daily;
import com.eoa.daily.domain.vo.DailyVo;

import java.util.List;

public interface DailyMapper {
    int insertDaily(Daily daily);

    int updateDailyStatus(Daily daily);

    Daily selectAuditList(Long dailyId);

    List<Daily> selectResultList(Daily daily);

    List<Daily> historyApproveList(Long auditId);
}
