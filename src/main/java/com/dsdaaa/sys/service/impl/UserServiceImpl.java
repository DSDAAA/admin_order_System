package com.dsdaaa.sys.service.impl;

import com.dsdaaa.sys.entity.User;
import com.dsdaaa.sys.mapper.UserMapper;
import com.dsdaaa.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DunstonAAA
 * @since 2023-08-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
