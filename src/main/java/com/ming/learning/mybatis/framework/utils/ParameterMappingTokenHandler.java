package com.ming.learning.mybatis.framework.utils;

import com.ming.learning.mybatis.framework.config.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数处理器
 * @Author ming
 * @time 2020/8/17 15:33
 */
public class ParameterMappingTokenHandler implements TokenHandler{

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    //content 时参数名称
    //content 就是#{}中的内容
    @Override
    public String handlerToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content){
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return  parameterMapping;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
