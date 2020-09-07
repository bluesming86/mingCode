package com.ming.learning.mybatis.framework.config;

import lombok.Data;

/**
 * 封装了#{} 解析出来的参数名称和参数类型
 * @Author ming
 * @time 2020/8/13 9:25
 */
@Data
public class ParameterMapping {

    private String name;
    private Class type;

    public ParameterMapping(String name, Class type) {
        this.name = name;
        this.type = type;
    }

    public ParameterMapping(String name) {
        this.name = name;
    }
}
