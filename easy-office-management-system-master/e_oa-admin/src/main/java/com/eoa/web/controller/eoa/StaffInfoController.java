package com.eoa.web.controller.eoa;

import com.eoa.common.core.controller.BaseController;
import com.eoa.common.core.domain.AjaxResult;
import com.eoa.common.core.domain.entity.SysUser;
import com.eoa.common.core.page.TableDataInfo;
import com.eoa.system.domain.vo.SysUserVo;
import com.eoa.system.service.ISysDeptService;
import com.eoa.system.service.ISysPostService;
import com.eoa.system.service.ISysRoleService;
import com.eoa.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eoa/staffinfo")
public class StaffInfoController extends BaseController {

    @Autowired
    private ISysUserService userService;


    @Autowired
    private ISysDeptService deptService;


    /**
     * 获取用户列表
     */
    @PreAuthorize("hasPermission('eoa:staffinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUserVo> list = userService.selectDeptUserList(user);
        return getDataTable(list);
    }

    /**
     * 获取当前用户公司
     */
    @PreAuthorize("hasPermission('eoa:staffinfo:list')")
    @GetMapping("/get/dept")
    public AjaxResult getDeptsUser() {
        return deptService.getDeptsUser();
    }
}
