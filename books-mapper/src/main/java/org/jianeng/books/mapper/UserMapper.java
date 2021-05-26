package org.jianeng.books.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jianeng.books.model.User;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 15:42
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
