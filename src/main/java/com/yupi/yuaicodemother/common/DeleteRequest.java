package com.yupi.yuaicodemother.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 处理删除数据的请求，传参为要删除数据的id
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
