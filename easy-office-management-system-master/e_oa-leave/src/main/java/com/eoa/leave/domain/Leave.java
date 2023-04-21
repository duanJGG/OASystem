package com.eoa.leave.domain;

import com.eoa.common.annotation.Excel;
import com.eoa.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Leave extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 请假id */
    private Long leaveId;

    /** 请假姓名 */
    @Excel(name = "请假姓名")
    private String leaveName;

    /** 请假原因 */
    @Excel(name = "请假原因")
    private String leaveReason;

    /** 请假天数 */
    @Excel(name = "请假天数")
    private Long leaveDays;

    /** 请假天数 */
    @Excel(name = "请假小时")
    private Long leaveHours;

    private String taskId;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime beginTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDateTime endTime;

    /** 状态（1通过 0 拒绝 2待审批 3驳回重填） */
    @Excel(name = "状态", readConverterExp = "1=通过,0=,拒=绝,2=待审批,3=驳回重填")
    private Integer status;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditName;

    /** 审核人id */
    @Excel(name = "审核人id")
    private Long auditId;

    /** 拒绝、驳回原因 */
    @Excel(name = "拒绝、驳回原因")
    private String reason;

    /** 逻辑删除 */
    private Integer delFlag;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Long getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Long leaveDays) {
        this.leaveDays = leaveDays;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Long getLeaveHours() {
        return leaveHours;
    }

    public void setLeaveHours(Long leaveHours) {
        this.leaveHours = leaveHours;
    }
}
