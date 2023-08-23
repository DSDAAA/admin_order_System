package com.dsdaaa.sys.mapper;

import com.dsdaaa.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author DunstonAAA
 * @since 2023-08-22
 */
public interface UserMapper extends BaseMapper<User> {
    public List<String> getRoleNameByUserId(Integer userId);
}
