package com.eoa.todo.service;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.todo.domain.Todo;
import com.eoa.todo.domain.vo.TodoVo;

import java.util.List;

public interface ITodoService {
    /**
     * 查询待办
     */
    public TodoVo selectTodoByTodoId(Long todoId);

    /**
     * 查询待办列表
     */
    public List<TodoVo> selectTodoList(Todo todo);

    /**
     * 新增待办
     */
    public AjaxResult insertTodo(TodoVo todoVo);

    /**
     * 修改待办
     */
    public AjaxResult updateTodo(TodoVo todoVo);

    /**
     * 批量删除待办
     */
    public int deleteTodoByTodoIds(Long[] todoIds);

    AjaxResult editStatus(Long todoId);
}
