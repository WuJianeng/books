package org.jianeng.books.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jianeng.books.model.UserAddress;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 22:23
 */
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {
}
