package com.eoa.leave.domain.vo;

import com.eoa.common.annotation.Excel;
import com.eoa.common.validator.CreateGroup;
import com.eoa.common.validator.EditGroup;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class LeaveVo {
    /** 请假id */
    //@NotNull(message = "{leave.id.notnull}",groups = EditGroup.class)
    private Long leaveId;

    /** 请假原因 */
    @NotBlank(message = "{leave.leaveReason.notblank}", groups = {CreateGroup.class, EditGroup.class})
    @Size(max = 400, message = "{leave.leaveReason.size}", groups = {CreateGroup.class, EditGroup.class})
    private String leaveReason;
    

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @NotNull(message = "{leave.beginTime.notnull}",groups = {CreateGroup.class, EditGroup.class})
    private LocalDateTime beginTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @NotNull(message = "{leave.endTime.notnull}",groups = {CreateGroup.class, EditGroup.class})
    private LocalDateTime endTime;

    /** 状态（1通过 0 拒绝 2待审批 3驳回重填） */
    private Integer status;
    
    
    /** 拒绝、驳回原因 */
    private String reason;

    private String taskId;

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

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
