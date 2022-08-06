package org.jianeng.books.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2022/8/6 22:33
 */
@Data
public class MoveBooks {
    private List<Integer> bookIds;

    private Integer addressId;

    private Integer userId;
}
