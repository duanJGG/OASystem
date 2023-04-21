package com.eoa.schedule.domain.vo;

import com.eoa.common.validator.CreateGroup;
import com.eoa.common.validator.EditGroup;
import com.eoa.common.validator.EditStatusGroup;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ScheduleVo {
    private static final long serialVersionUID = 1L;

    /** 日程id */
    @NotNull(message = "{schedule.id.notnull}", groups = EditGroup.class)
    private Long scheduleId;

    /** 开始时间 */
    @NotNull(message = "{schedule.beginTime.notnull}", groups = {CreateGroup.class, EditGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime beginTime;

    /** 结束时间 */
    @NotNull(message = "{schedule.endTime.notnull}", groups = {CreateGroup.class, EditGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime endTime;

    /** 目的地 */
    @NotBlank(message = "{schedule.place.notblank}", groups = {CreateGroup.class, EditGroup.class})
    private String place;

    /** 内容 */
    @NotBlank(message = "{schedule.content.notblank}", groups = {CreateGroup.class, EditGroup.class})
    @Size(max = 200, message = "{schedule.content.size}", groups = {CreateGroup.class, EditGroup.class})
    private String content;

    /** 陪同人员 */
    @NotBlank(message = "{schedule.accompany.notblank}", groups = {CreateGroup.class, EditGroup.class})
    private String accompany;

    /** 状态（0进行中 1已完成） */
    @NotNull(message = "{schedule.status.notnull}", groups = {EditStatusGroup.class})
    private Integer status;

    /** 备注信息 */
    private String remark;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccompany() {
        return accompany;
    }

    public void setAccompany(String accompany) {
        this.accompany = accompany;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
