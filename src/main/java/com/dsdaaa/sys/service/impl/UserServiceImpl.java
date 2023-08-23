package com.dsdaaa.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dsdaaa.sys.entity.User;
import com.dsdaaa.sys.mapper.UserMapper;
import com.dsdaaa.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplate redisTemplate;

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
            //存入Redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key, loginUser, 30, TimeUnit.MINUTES);
            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            return data;
        }

        return null;
    }


    @Override
    public Map<String, Object> getUserInfo(String token) {
        //根据token获取用户信息,从Redis中获取
        Object object = redisTemplate.opsForValue().get(token);
        if (object != null) {
            User loginUser = JSON.parseObject(JSON.toJSONString(object), User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getUsername());
            data.put("avatar", loginUser.getAvatar());
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles", roleList);

            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
