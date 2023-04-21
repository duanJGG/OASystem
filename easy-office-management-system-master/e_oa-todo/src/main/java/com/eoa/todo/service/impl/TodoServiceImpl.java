package com.eoa.todo.service.impl;

import com.eoa.common.core.domain.AjaxResult;
import com.eoa.common.utils.DateUtils;
import com.eoa.common.utils.SecurityUtils;
import com.eoa.todo.domain.vo.TodoVo;
import com.eoa.todo.mapper.TodoMapper;
import com.eoa.todo.service.ITodoService;
import com.eoa.todo.domain.Todo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TodoServiceImpl implements ITodoService {
    @Autowired
    private TodoMapper todoMapper;

    /**
     * 查询待办
     */
    @Override
    public TodoVo selectTodoByTodoId(Long todoId) {
        return todoMapper.selectTodoByTodoId(todoId);
    }

    /**
     * 查询待办列表
     */
    @Override
    public List<TodoVo> selectTodoList(Todo todo) {
        todo.setUserId(SecurityUtils.getUserId());
        return todoMapper.selectTodoList(todo);
    }

    /**
     * 新增待办
     */
    @Override
    public AjaxResult insertTodo(TodoVo todoVo) {
        var todo = new Todo();
        BeanUtils.copyProperties(todoVo,todo);
        todo.setStatus(2);
        todo.setUserId(SecurityUtils.getUserId());
        todo.setCreateTime(LocalDateTime.now());
        todo.setDelFlag(0);
        return  todoMapper.insertTodo(todo) > 0 ? AjaxResult.success("添加成功"):AjaxResult.error("添加失败");
    }

    /**
     * 修改待办
     */
    @Override
    public AjaxResult updateTodo(TodoVo todoVo) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoVo,todo);
        return todoMapper.updateTodo(todo) > 0 ? AjaxResult.success("修改成功"):AjaxResult.error("修改失败");
    }

    /**
     * 批量删除待办
     */
    @Override
    public int deleteTodoByTodoIds(Long[] todoIds) {
        return todoMapper.deleteTodoByTodoIds(todoIds);
    }

    @Override
    public AjaxResult editStatus(Long todoId) {
        return todoMapper.editStatus(todoId)>0 ? AjaxResult.success("操作成功"):AjaxResult.error("操作失败");
    }
}
