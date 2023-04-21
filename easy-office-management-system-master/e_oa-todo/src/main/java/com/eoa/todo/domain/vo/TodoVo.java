package com.eoa.todo.domain.vo;

import com.eoa.common.core.domain.BaseEntity;
import com.eoa.common.validator.CreateGroup;
import com.eoa.common.validator.EditGroup;
import com.eoa.common.validator.EditStatusGroup;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class TodoVo {

    /**
     * 待办id
     */
    @NotNull(message = "{todo.id.notnull}", groups = EditGroup.class)
    private Long todoId;

    /**
     * 标题
     */
    @NotBlank(message = "{todo.title.notblank}", groups = {CreateGroup.class, EditGroup.class})
    @Size(max = 30, message = "{todo.title.size}", groups = {CreateGroup.class, EditGroup.class})
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "{todo.content.notblank}", groups = {CreateGroup.class, EditGroup.class})
    @Size(max = 400, message = "{todo.content.size}", groups = {CreateGroup.class, EditGroup.class})
    private String content;

    /**
     * 完成时间
     */
    @NotNull(message = "{todo.finishTime.notnull}", groups = {CreateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime finishTime;

    /**
     * 状态（0未完成 1完成 2进行中）
     */
    @NotNull(message = "{todo.status.notnull}", groups = {EditStatusGroup.class})
    private Integer status;


    private Long endDay;

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
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

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getEndDay() {
        return endDay;
    }

    public void setEndDay(Long endDay) {
        this.endDay = endDay;
    }
}
