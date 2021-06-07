package org.jianeng.books.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/5 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAuditBase {
    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;
}
