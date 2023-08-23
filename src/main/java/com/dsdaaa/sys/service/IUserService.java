package com.dsdaaa.sys.service;

import com.dsdaaa.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author DunstonAAA
 * @since 2023-08-22
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);
    

    Map<String, Object> getUserInfo(String token);

    void logout(String token);
}
