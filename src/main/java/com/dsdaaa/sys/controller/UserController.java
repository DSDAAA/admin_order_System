package com.dsdaaa.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsdaaa.common.vo.Result;
import com.dsdaaa.sys.entity.User;
import com.dsdaaa.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author DunstonAAA
 * @since 2023-08-22
 */
@RestController
@RequestMapping("/user")
//@CrossOrigin 跨域处理
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public Result<List<User>> getAllUser() {
        List<User> list = userService.list();
        return Result.success(list, "查询成功");
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = userService.login(user);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20003, "用户名或密码错误");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<?> getUserInfo(@RequestParam("token") String token) {
        //根据token获取用户信息,从Redis中获取
        Map<String, Object> data = userService.getUserInfo(token);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20005, "无效的用户凭证，请重新登录");
    }

    @GetMapping("list")
    public Result<Map<String, Object>> getUserList(@RequestParam(value = "username", required = false) String username,
                                                   @RequestParam(value = "phone", required = false) String phone,
                                                   @RequestParam(value = "pageNo") Long pageNo,
                                                   @RequestParam(value = "pageSize") Long pageSize) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username), User::getUsername, username);
        wrapper.eq(StringUtils.hasLength(phone), User::getPhone, phone);

        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);
    }
}
