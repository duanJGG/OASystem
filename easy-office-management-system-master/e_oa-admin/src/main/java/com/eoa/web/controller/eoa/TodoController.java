package com.eoa.web.controller.eoa;

import com.eoa.common.annotation.Log;
import com.eoa.common.core.controller.BaseController;
import com.eoa.common.core.domain.AjaxResult;
import com.eoa.common.core.page.TableDataInfo;
import com.eoa.common.enums.BusinessType;
import com.eoa.common.validator.CreateGroup;
import com.eoa.common.validator.EditGroup;
import com.eoa.common.validator.EditStatusGroup;
import com.eoa.todo.domain.Todo;
import com.eoa.todo.domain.vo.TodoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.eoa.todo.service.ITodoService;

import java.util.List;

@RestController
@RequestMapping("/eoa/todo")
public class TodoController extends BaseController {


    @Autowired
    private ITodoService todoService;

    /**
     * 新增待办
     */
    @PreAuthorize("hasPermission('eoa:todo:add')")
    @Log(title = "新增待办", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody TodoVo todoVo) {
        return todoService.insertTodo(todoVo);
    }

    /**
     * 查询待办列表
     */
    @PreAuthorize("hasPermission('eoa:todo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Todo todo) {
        startPage();
        List<TodoVo> list = todoService.selectTodoList(todo);
        return getDataTable(list);
    }

    /**
     * 获取待办详细信息
     */
    @PreAuthorize("hasPermission('eoa:todo:query')")
    @GetMapping(value = "/{todoId}")
    public AjaxResult getInfo(@PathVariable("todoId") Long todoId) {
        return success(todoService.selectTodoByTodoId(todoId));
    }

    /**
     * 修改待办
     */
    @PreAuthorize("hasPermission('eoa:todo:edit')")
    @Log(title = "修改待办", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody TodoVo todoVo) {
        return todoService.updateTodo(todoVo);
    }

    /**
     * 修改待办
     */
    @PreAuthorize("hasPermission('eoa:todo:edit')")
    @Log(title = "修改待办状态", businessType = BusinessType.UPDATE)
    @PutMapping("status/{todoId}")
    public AjaxResult editStatus(@Validated(EditStatusGroup.class) @PathVariable("todoId") Long todoId) {
        return todoService.editStatus(todoId);
    }
    /**
     * 删除待办
     */
    @PreAuthorize("hasPermission('eoa:todo:remove')")
    @Log(title = "删除待办", businessType = BusinessType.DELETE)
    @DeleteMapping("/{todoIds}")
    public AjaxResult remove(@PathVariable Long[] todoIds) {
        return toAjax(todoService.deleteTodoByTodoIds(todoIds));
    }
}
