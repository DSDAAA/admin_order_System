package com.dsdaaa.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dsdaaa.sys.entity.User;
import com.dsdaaa.sys.mapper.UserMapper;
import com.dsdaaa.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author DunstonAAA
 * @since 2023-08-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Map<String, Object> login(User user) {
        //根据用户名和密码进行查询
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        userLambdaQueryWrapper.eq(User::getPassword, user.getPassword());
        User loginUser = this.baseMapper.selectOne(userLambdaQueryWrapper);
        if (loginUser != null) {
            //暂时用UUID,终极方案是jwt
            String key = "user:" + UUID.randomUUID();
            //
            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            return data;
        }

        return null;
    }
}
