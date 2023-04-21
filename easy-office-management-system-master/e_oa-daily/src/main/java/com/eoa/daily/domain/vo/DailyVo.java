package com.eoa.daily.domain.vo;


import com.eoa.common.validator.CreateGroup;
import com.eoa.common.validator.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DailyVo {

    /** 日报id */
    @NotNull(message = "{daily.id.notnull}",groups = EditGroup.class)
    private Long dailyId;

    /**
     * 日报流程id
     */
    private String taskId;

    /** 标题 */
    @NotBlank(message = "{daily.title.notblank}", groups = {CreateGroup.class, EditGroup.class})
    @Size(max = 30, message = "{daily.title.size}", groups = {CreateGroup.class, EditGroup.class})
    private String title;

    /** 工作内容 */
    @NotBlank(message = "{daily.content.notblank}", groups = {CreateGroup.class, EditGroup.class})
    @Size(max = 400, message = "{daily.content.size}", groups = {CreateGroup.class, EditGroup.class})
    private String content;

    /** 状态（1 通过 0 驳回） */
    private Integer status;

    /** 驳回原因 */
    private String reason;

    /** 审核人 */
    private String auditName;

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
