package com.ming.learning.mybatis.framework.sqlNode;

import com.ming.learning.mybatis.framework.config.DynamicContext;
import com.ming.learning.mybatis.framework.utils.GenericTokenParser;
import com.ming.learning.mybatis.framework.utils.OgnlUtils;
import com.ming.learning.mybatis.framework.utils.SimpleTypeRegistry;
import com.ming.learning.mybatis.framework.utils.TokenHandler;

import java.util.Map;

/**
 * 封装了带有有${} 的sql文本
 * @Author ming
 * @time 2020/8/14 15:39
 */
public class TextSqlNode implements SqlNode {


    private String sqlText;

    public TextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        // 处理${}
        GenericTokenParser tokenParser = new GenericTokenParser("${","}", new BindingTokenHandler(context));
    }

    public boolean isDynamic() {
        if (sqlText != null){
            return sqlText.indexOf("${") >= 0;
        }
        return false;
    }

    /**
     * 处理${}中内容
     */
    class BindingTokenHandler implements TokenHandler{

        //为了获取入参对象
        private DynamicContext context;

        public BindingTokenHandler(DynamicContext context) {
            this.context = context;
        }

        /**
         *
         * @param content 这就是${}中的参数名称
         * @return
         */
        @Override
        public String handlerToken(String content) {
            Object parameter = context.getBindings().get("_parameter");
            if (SimpleTypeRegistry.isSimpleType(parameter.getClass())){
                return parameter.toString();
            }

            // 使用Ognl表达式获取值
            Object value = OgnlUtils.getValue(content, parameter);
            return value == null ? "": value.toString();
        }
    }
}
