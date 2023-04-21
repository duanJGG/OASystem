package com.eoa.daily.domain;

import com.eoa.common.annotation.Excel;
import com.eoa.common.core.domain.BaseEntity;

public class Daily extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 日报id */
    private Long dailyId;

    /**
     * 日报流程id
     */
    private String taskId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 工作内容 */
    @Excel(name = "工作内容")
    private String content;

    /** 状态（1 通过 0 驳回） */
    @Excel(name = "状态", readConverterExp = "1=,通=过,0=,驳=回")
    private Integer status;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 驳回原因 */
    @Excel(name = "驳回原因")
    private String reason;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditName;

    private Long auditId;

    /** 逻辑删除 */
    private Integer delFlag;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getDailyId() {
        return dailyId;
    }

    public void setDailyId(Long dailyId) {
        this.dailyId = dailyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
