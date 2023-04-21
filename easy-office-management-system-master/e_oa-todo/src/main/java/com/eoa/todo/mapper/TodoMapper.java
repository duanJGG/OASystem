package com.eoa.todo.mapper;

import com.eoa.todo.domain.Todo;
import com.eoa.todo.domain.vo.TodoVo;

import java.util.List;

public interface TodoMapper {
    /**
     * 查询待办
     *
     */
    public TodoVo selectTodoByTodoId(Long todoId);

    /**
     * 查询待办列表
     *
     */
    public List<TodoVo> selectTodoList(Todo todo);

    /**
     * 新增待办
     *
     */
    public int insertTodo(Todo todo);

    /**
     * 修改待办
     *
     */
    public int updateTodo(Todo todo);


    /**
     * 批量删除待办
     *
     */
    public int deleteTodoByTodoIds(Long[] todoIds);

    int editStatus(Long todoId);
}
