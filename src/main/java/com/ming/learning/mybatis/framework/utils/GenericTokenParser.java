package com.ming.learning.mybatis.framework.utils;

/**
 * @Author ming
 * @time 2020/8/17 15:28
 */
public class GenericTokenParser {

    private final String openToken;
    private final String closeToken;
    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    /**
     * 解析${} 和 #{}
     * @param text
     * @return
     */
    public String parse(String text){
        if (text == null || text.isEmpty()){
            return "";
        }

        return null;
    }
}
