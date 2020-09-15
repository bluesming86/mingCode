# 封装类

## BaseVO

```java
package org.soul.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.soul.commons.bean.IEntity;
import org.soul.commons.init.context.AbstractBaseVo;
import org.soul.commons.lang.GenericTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.sort.Order;
import org.soul.commons.query.sort.Sort;
import org.soul.commons.security.CryptoTool;
import org.soul.model.log.audit.vo.BaseLog;
import org.soul.model.log.audit.vo.LogVo;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装交互的值的基础类
 *
 * @author Kevice
 * @date 2015/5/6.
 * @since 1.0.0
 */
public class BaseVo<S extends IEntity, Q extends IQuery<S>> extends AbstractBaseVo implements Serializable {

    private static final long serialVersionUID = -6086989400504234648L;
    /**
     * 是否成功
     */
    private boolean success = true;
    /**
     * 成功消息
     */
    private String okMsg;
    /**
     * 错误消息
     */
    private String errMsg;
    /**
     * 查询条件
     */
    private S searchObject;
    /**
     * 查询条件
     */
    private Q query;

    /**
     * 表单验证规则
     */
    private String validateRule;
    /**
     * 数据库操作时，include或exclude的属性名数组
     */
    private String[] properties = new String[]{};

    public BaseVo() {


    }


    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String... properties) {
        this.properties = properties;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOkMsg() {
        return okMsg;
    }

    public void setOkMsg(String okMsg) {
        this.okMsg = okMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    public String getSearchId(Object id){
        return CryptoTool.aesEncrypt(String.valueOf(id), "BaseVo");
    }
    public void setSearchId(String searchId){
        if(getSearch().getId() instanceof Long) {
            getSearch().setId(Long.valueOf(CryptoTool.aesDecrypt(searchId, "BaseVo")));
        }else
        {
            getSearch().setId(Integer.valueOf(CryptoTool.aesDecrypt(searchId, "BaseVo")));
        }
    }
    public S getSearch() {
        if (searchObject == null) {
            Class<S> searchClass = (Class<S>) GenericTool.getSuperClassGenricType(getClass(), 1);
            try {
                searchObject = searchClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return searchObject;
    }

    public void setSearch(S search) {
        this.searchObject = search;
    }

    public Q getQuery() {
        if (query == null) {
            Class<Q> criteriaClass = (Class<Q>) GenericTool.getSuperClassGenricType(getClass(), 2);
            try {
                Constructor<?> constructor = criteriaClass.getDeclaredConstructors()[0];
                constructor.setAccessible(true);
                query = (Q) constructor.newInstance();
                query.setSearch(getSearch());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return query;
    }

    public String getValidateRule() {
        return validateRule;
    }

    public void setValidateRule(String validateRule) {
        this.validateRule = validateRule;
    }

    /**
     * 将Criteria、Sort对象转成mybatis的查询子句(预编译)
     *
     * @return key和value的情况如下：<br>
     *       1.key为where，value为where子句 <br>
     *       2.key为sort，value为order by子句 <br>
     *       3.key为小写列名,value为列的值 <br>
     */
    @JsonIgnore
    public Map<String, Object> getQueryParams() {
        return getQueryParams(null, null);
    }

    /**
     * 将Criteria、Sort对象转成mybatis的查询子句(预编译)
     *
     * @param criteria 为null时取getFinalCriteria()的结果值
     * @return key和value的情况如下：<br>
     *       1.key为where，value为where子句 <br>
     *       2.key为sort，value为order by子句 <br>
     *       3.key为小写列名,value为列的值 <br>
     */
    @JsonIgnore
    public Map<String, Object> getQueryParams(Criteria criteria) {
        return getQueryParams(criteria, null);
    }

    /**
     * 将Criteria、Sort对象转成mybatis的查询子句(预编译)
     *
     * @param criteria 为null时取getFinalCriteria()的结果值
     * @param columnMap Map<属性名，列名>，为null时列名将根据属性名由驼峰规则转成下划线
     * @return key和value的情况如下：<br>
     *       1.key为where，value为where子句 <br>
     *       2.key为sort，value为order by子句 <br>
     *       3.key为小写列名,value为列的值 <br>
     */
    @JsonIgnore
    public Map<String, Object> getQueryParams(Criteria criteria, Map<String, String> columnMap) {
        Q query = getQuery();
        if (criteria == null) {
            criteria = query.getFinalCriteria();
        }
        Map<String, Object> paramMap;
        if (criteria == null) {
            paramMap = new HashMap<>();
        } else {
            paramMap = criteria.toParamMap(columnMap);
        }
        if (paramMap.isEmpty()) {
            paramMap.put("where", "1=1");
        }
        Order[] orders = query.getOrders();
        if (orders!=null && orders.length>0) {
            paramMap.put("sort", Sort.toSql(orders, columnMap));
        }
        return paramMap;
    }


    //region audit log
    private LogVo logVo;

    /**
     * 添加业务级的日志
     * @return
     * @author Longer
     */
    public BaseLog addBussLog(){
        if (logVo == null) {
            logVo = new LogVo();
        }
        return logVo.addBussLog();
    }

    /**
     * 添加业务级的日志
     * @return
     * @author Longer
     */
    public BaseLog addSysLog(){
        if (logVo == null) {
            logVo = new LogVo();
        }
        return logVo.addSysLog();
    }

    /**
     * 获取日志组
     * @return
     * @author Longer
     */
    @JsonIgnore
    public LogVo getLogVo(){
        return logVo;
    }

    //endregion log
}
```

```java
package org.soul.commons.init.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by longer on 3/19/15.
 * Dubbo通讯隐藏字段
 *
 */
public abstract class AbstractBaseVo implements Serializable {

   private static final long serialVersionUID = -7340209125558108205L;

   private Integer _entrance;    // 数据源入口
   private Integer _userId;      // 用户ID
   private Integer _userOwnerId; // 用户上级ID.
   private String _userType;     // 用户类型
   private Locale _locale;          // 用户地区
   private TimeZone _timeZone;       // 用户时区
   private Operator _operator;    // 日志操作员

   private Integer _siteId;      // 站点id
   private Integer    _switchSiteId;  //切换的站点id
   private Integer _siteUserId;   // 站长id
   private String     _siteCode;    // 站点代码
   private String     _userIp;      // 用户Ip
   private Integer _siteParentId; // 运营商ID
   private Locale  _siteLocale;   // 站点地区
   private TimeZone _siteTimeZone;    // 站点时区

   private String  _sessionId;       // sessionId
   private Integer _sessionSiteId;    // 用于session前缀,不一定是当前上下文中的siteId(站长可以切换上下文中的siteId)
   private Integer _dataSourceId; // 指定数据源id

   private String _dubboVersion;    // dubbo version
   private boolean    _isIgnoreAudit;
   private Map<String,Object> _params=new HashMap<>();
   /**
    * 本次操作的唯一键值，用于追踪一系列操作的日志
    */
   private String        _opKey;
   /**
    * 虚拟的站点ID.
    */
   private Integer _virtualSiteId;
   /**
    * 获取是否忽略审计日志
    * @return
    */
   public boolean _getIsIgnoreAudit() {
      return _isIgnoreAudit;
   }

   /**
    * 设置是否忽略审计日志
    * @param isIgnoreAudit
    */
   public void _setIsIgnoreAudit(boolean isIgnoreAudit) {
      this._isIgnoreAudit= isIgnoreAudit;
   }

   public Integer _getSiteUserId() {
      return _siteUserId;
   }
   public void _setSiteUserId(Integer siteUserId) {
      this._siteUserId = siteUserId;
   }

   public Locale _getSiteLocale() {
      return _siteLocale;
   }
   public void _setSiteLocale(Locale _siteLocale) {
      this._siteLocale = _siteLocale;
   }

   public Locale _getLocale() {
      return _locale;
   }
   public void _setLocale(Locale locale) {
      this._locale = locale;
   }

   public TimeZone _getTimeZone() {
      return _timeZone;
   }
   public void _setTimeZone(TimeZone timeZone) {
      this._timeZone = timeZone;
   }

   public Integer _getEntrance() {
      return _entrance;
   }
   public void _setEntrance(Integer userType) {
      this._entrance = userType;
   }
   public String _getSiteIdString() {
      if(_switchSiteId!=null){
         return String.valueOf(_switchSiteId);
      }
      return String.valueOf(_siteId);
   }
   public Integer _getSiteId() {
      if(_switchSiteId!=null){
         return _switchSiteId;
      }
      return _siteId;
   }
   public void _setSiteId(Integer siteId) {
      this._siteId = siteId;
   }

   public Integer _getSiteParentId() {
      return _siteParentId;
   }
   public void _setSiteParentId(Integer siteParentId) {
      this._siteParentId = siteParentId;
   }

   public String _getSessionId() {
      return _sessionId;
   }
   public void _setSessionId(String _sessionId) {
      this._sessionId = _sessionId;
   }

   /**
    * warning: Service层默认无法获取此对象
    *           当且仅当Session接口被注解成:@Audit
    * @return
    */
   public Operator _getOperator() {
      return _operator;
   }
   public void _setOperator(Operator _operator) {
      this._operator = _operator;
   }

   public Integer _getUserId() {
      return _userId;
   }
   public void _setUserId(Integer userId) {
      this._userId = userId;
   }

   public Integer _getUserOwnerId() {
      return _userOwnerId;
   }
   public String _getSiteCode() {
      return _siteCode;
   }
   public void _setSiteCode(String _siteCode) {
      this._siteCode = _siteCode;
   }

   public TimeZone _getSiteTimeZone() {
      return _siteTimeZone;
   }
   public void _setSiteTimeZone(TimeZone _siteTimeZone) {
      this._siteTimeZone = _siteTimeZone;
   }

   public Integer _getDataSourceId() {
      return this._dataSourceId;
   }
   public void _setDataSourceId(Integer dataSourceId) {
      this._dataSourceId = dataSourceId;
   }

   public String _getDubboVersion() {
      return _dubboVersion;
   }
   public void _setDubboVersion(String dubboVersion) {
      this._dubboVersion = _dubboVersion;
   }

   public Integer _getSessionSiteId() {
      return _sessionSiteId;
   }
   public void _setSessionSiteId(Integer _sessionSiteId) {
      this._sessionSiteId = _sessionSiteId;
   }

   public String _getUserType() {
      return _userType;
   }
   public void _setUserType(String _userType) {
      this._userType = _userType;
   }

   public String _getUserIp() {
      return _userIp;
   }
   public void _setUserIp(String _userIp) {
      this._userIp = _userIp;
   }

   public Integer _getswitchSiteId() {
      return _switchSiteId;
   }
   public void _setswitchSiteId(Integer _switchSiteId) {
      this._switchSiteId = _switchSiteId;
   }
   public String _getOpKey() {
      return _opKey;
   }

   public void _setOpKey(String opKey) {
      this._opKey = opKey;
   }

   /**
    * 设置上下文参数
    *
    * @param contextParam 上下文参数对象
    * @author Kevice
    */
   public void _setContextParam(ContextParam contextParam) {
      _siteUserId = contextParam.getSiteUserId();
      _siteId = contextParam.getSiteId();
      _siteCode = contextParam.getSiteCode();
      _siteTimeZone = contextParam.getSiteTimeZone();
      _siteParentId = contextParam.getSiteParentId();
      _entrance = contextParam.getEntrance();
      _timeZone = contextParam.getTimeZone();
      _sessionId = contextParam.getSessionId();
      _userId = contextParam.getUserId();
      _userOwnerId=contextParam.getUserOwnerId();
      _userType = contextParam.getUserType();
      _operator = contextParam.getOperator();
      _locale = contextParam.getLocale();
      _siteLocale = contextParam.getSiteLocale();
      _dubboVersion = contextParam.getDubboVersion();
      _sessionSiteId = contextParam.getSessionSiteId();
      _isIgnoreAudit = contextParam.getIsIgnoreAudit();
      _operator =contextParam.getOperator();
      _switchSiteId=contextParam.getSwitchSiteId();
      _userIp=contextParam.getUserIp();
      _opKey=contextParam.getOpKey();
      _virtualSiteId=contextParam.getVirtualSiteId();
      _params.putAll(contextParam.getExtendProperties());
   }

   /**
    * 从vo到上下文
    * @return
    */
   public ContextParam _getContextParam(){
      ContextParam contextParam = new ContextParam();
      contextParam.setSiteUserId(_getSiteUserId());
      contextParam.setLocale(_getLocale());
      contextParam.setTimeZone(_getTimeZone());
      contextParam.setSiteTimeZone(_getSiteTimeZone());
      contextParam.setSiteId(_getSiteId());
      contextParam.setSiteCode(_getSiteCode());
      contextParam.setEntrance(_getEntrance());
      contextParam.setSiteParentId(_getSiteParentId());
      contextParam.setSessionId(_getSessionId());
      contextParam.setUserId(_getUserId());
      contextParam.setUserOwnerId(_getUserOwnerId());
      contextParam.setUserType(_getUserType());
      contextParam.setSiteLocale(_getSiteLocale());
      contextParam.setDubboVersion(_getDubboVersion());
      contextParam.setSessionSiteId(_getSessionSiteId());
      contextParam.setOperator(_getOperator());
      contextParam.setSwitchSiteId(_getswitchSiteId());
      contextParam.setUserIp(_getUserIp());
      contextParam.setVirtualSiteId(_getVirtualSiteId());
      contextParam.setOpKey(_getOpKey());
      contextParam.setExtendProperties(_getParams());
      return contextParam;
   }
   public void _setParams(Map<String,Object> params)
   {
      this._params=params;
   }
   public Map<String, Object> _getParams() {
      return _params;
   }

   public Integer _getVirtualSiteId() {
      return _virtualSiteId;
   }

   public void _setVirtualSiteId(Integer _virtualSiteId) {
      this._virtualSiteId = _virtualSiteId;
   }
}
```

```java
package org.soul.commons.bean;

import java.io.Serializable;

/**
 * 持久化实体对象接口
 * 
 * @since 1.0.0
 * @author <b>Kevice</b>
 */
public interface IEntity<T> extends Serializable {
   
   /**
    * 取得主键
    */
   T getId();
   
   /**
    * 设置主键
    */
   void setId(T id);
   
}
```



```java
package org.soul.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.soul.commons.bean.IEntity;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.sort.Order;

import java.io.Serializable;

/** 
 */
public interface IQuery<E extends IEntity> extends Serializable {
    void setSearch(E search);
    @JsonIgnore
    Criteria getCriteria();
    @JsonIgnore
    Criteria getFinalCriteria();
    Order[] getOrders();
    Criterion[] getCriterions();
    void setCriterions(Criterion[] criterions);

}
```

## BaseListVo

```java
package org.soul.model.common;

import org.soul.commons.bean.IEntity;
import org.soul.commons.bean.Pair;
import org.soul.commons.collections.MapTool;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.Paging;
import org.soul.model.listop.po.SysListOperator;

import java.util.*;

/**
 * 封装列表操作相关交互的值的基础类 
 */
public class BaseListVo<E extends IEntity, S extends IEntity, Q extends IQuery<S>> extends BaseVo<S, Q> {

    private static final long serialVersionUID = 1648191716838776010L;
    /**
     * 分页信息
     */
    private Paging paging = new Paging();
    /**
     * 列表所有自定义列方案可视的属性对象, Map<方案id, 可视的属性对象>
     */
    private Map<String, FieldProperty> allFieldLists = new LinkedHashMap<>(0,1f);
    /**
     * 列表中当前自定义列方案所有可视的属性对象列表
     */
    private Map<String,FieldProperty> fields = new LinkedHashMap<>(0,1f);
    /**
     * 查询结果
     */
    private List<E> result = new ArrayList<>(0);
    /**
     * 选中的属性列表信息
     */
    private Integer selectFields;
    /**
     * 自定义查询条件
     */
    private Map<String, ?> conditions = new HashMap<>(0,1f);
    /**
     * 要查询的属性名
     */
    private String propertyName;
    /**
     * 要查询的属性名对应的值集合
     */
    private Collection<?> propertyValues;

    public BaseListVo() {
        paging.setPageSize(getDefaultPagingSize());
    }

    //region Conditions 单属性，多属性条件 And Or的封装

    /**
     * 自定义查询条件Map的封装，支持单条件，多条件
     *
     * @return
     */
    public Map<String, ?> getConditions() {
        return conditions;
    }

    /**
     * 自定义查询条件Map的封装，支持单条件，多条件
     *
     * @param conditions
     */
    public void setConditions(Map<String, ?> conditions) {
        this.conditions = conditions;
    }

    /**
     * 自定义查询条件Map的封装
     *
     * @param conditions
     */
    public void setConditions(Pair<String, ?>... conditions) {
        this.conditions = MapTool.newHashMap((Pair<String, Object>[]) conditions);
    }

    //endregion
    //region Paging

    /**
     * 获取分页对象
     *
     * @return
     */
    public Paging getPaging() {
        return paging;
    }

    /**
     * 设置分页对象
     *
     * @param paging
     */
    public void setPaging(Paging paging) {
        this.paging = paging;
    }
    //endregion
    //region 默认的查询结果列表

    /**
     * 默认的查询结果列表对象
     *
     * @return
     */
    public List<E> getResult() {
        return result;
    }

    /**
     * 设置查询结果
     *
     * @param result
     */
    public void setResult(List<E> result) {
        this.result = result;
    }
    //endregion

    /**
     * 获取默认的分页大小
     *
     * @return
     */
    public int getDefaultPagingSize() {
        return Paging.DEFAULT_PAGE_SIZE;
    }
    //region 自定义显示字段的封装

    /**
     * 获取默认字段列表属性
     *
     * @return
     */
    public Map<String,FieldProperty> getDefaultFields() {
        return new LinkedHashMap<>(0,1f);
    }

    /**
     * 获取全列表字段属性
     *
     * @return
     */
    public Map<String, FieldProperty> getAllFieldLists() {
        return allFieldLists;
    }

    /**
     * 返回要查询的属性名
     *
     * @return　要查询的属性名
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 设置要查询的属性名
     *
     * @param propertyName 要查询的属性名
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 返回要查询的属性名对应的值集合
     *
     * @return 要查询的属性名对应的值集合
     */
    public Collection<?> getPropertyValues() {
        return propertyValues;
    }

    /**
     * 设置要查询的属性名对应的值集合
     *
     * @param propertyValues 要查询的属性名对应的值集合
     */
    public void setPropertyValues(Collection<?> propertyValues) {
        this.propertyValues = propertyValues;
    }

    /**
     * 设置全列表字段属性
     *
     * @param listOP
     */
    public void setAllFieldLists(Map<String, SysListOperator> listOP) {
        fields.clear();
        if (listOP == null || listOP.size() == 0) {
            allFieldLists = new HashMap<>(0,1f);
            Map<String,FieldProperty> tempfields=getDefaultFields();
            for (String f  : tempfields.keySet()) {
                if (f != null && tempfields.get(f).getShowColumn()==true) {
                    fields.put(f, tempfields.get(f));
                }
            }
            return;
        }
        if (listOP.size() > 0) {
            if (selectFields != null && listOP.get(selectFields)!=null) {
                SysListOperator tmep= listOP.get(selectFields);
                ArrayList<Map> selected=(ArrayList<Map>) JsonTool.fromJson(tmep.getContent(), ArrayList.class);
                for (Map map : selected) {
                    String key=map.get("name").toString();
                    FieldProperty f = getDefaultFields().get(key);
                    if (f != null) {
                        fields.put(key, f);
                    }
                }
                return;
            }
        }

        SysListOperator tmep= listOP.values().iterator().next();
        selectFields = tmep.getId();
        ArrayList<Map> selected=(ArrayList<Map>) JsonTool.fromJson(tmep.getContent(), ArrayList.class);
        fields.clear();
        for (Map map : selected) {
            String key=map.get("name").toString();
            FieldProperty f = getDefaultFields().get(key);
            if (f != null) {
                fields.put(key, f);
            }
        }
    }

    /**
     * 获取选中的字段列表
     *
     * @return
     */
    public Integer getSelectFields() {
        return selectFields;
    }

    /**
     * 设置选中的字段列表
     *
     * @param selectFields
     */
    public void setSelectFields(Integer selectFields) {
        this.selectFields = selectFields;
    }

    /**
     * 获取选中的字段定义信息列表
     *
     * @return
     */
    public Map<String,FieldProperty> getFields() {

        if(fields==null || fields.size()==0)
        {
            Map<String,FieldProperty> allFeilds=getDefaultFields();
            for (String fieldProperty : allFeilds.keySet()) {
                FieldProperty f=allFeilds.get(fieldProperty);
                if (f.getShowColumn() && fields.get(f.getFeildName())==null) {
                    fields.put(f.getFeildName(), f);
                }
            }
        }
        return fields;
    }
    //endregion

    /**
     * 将Criteria、Paging、Sort对象转成mybatis的查询子句(预编译)
     *
     * @param criteria 为null时取getFinalCriteria()的结果值
     * @param columnMap Map<属性名，列名>，为null时列名将根据属性名由驼峰规则转成下划线
     * @return key和value的情况如下：<br>
     *       1.key为where，value为where子句 <br>
     *       2.key为sort，value为order by子句 <br>
     *       3.key为paging，value为分页子句 <br>
     *       4.key为小写列名,value为列的值 <br>
     *       5.key为pageNo,value为当前页码 <br>
     *       6.key为pageSize,value为当前页面大小
     */
    public Map<String, Object> getQueryParams(Criteria criteria, Map<String, String> columnMap) {
        Map<String, Object> params = super.getQueryParams(criteria, columnMap);
        if (getPaging()!= null) {
            params.put("pageNo", paging.getPageNumber());
            params.put("pageSize", paging.getPageSize());
            params.put("paging", paging.toSql());
        }
        return params;
    }
}
```

## Paging

```java
package org.soul.commons.query;

/**
 * 分页信息
 *
 * @since 1.0.0
 * @author Kevice
 * @time 2012-6-5 下午10:34:04
 */
public class Paging implements java.io.Serializable { // implements Pageable {

   public static final int DEFAULT_PAGE_SIZE = 20;
   private static final long serialVersionUID = -7506143827048977889L;

   // private int offset;
   private int pageNumber = 1; // 当前页码
   private int pageSize; // 每页记录数
   private long totalCount; // 总记录数
// private int pageCount; // 总页数
   private int slider = 1; // 前后显示页面长度
   private int midLength = 5; // 显示页面长度
   private int midBeginPage; // 中间要显示的页的开始页码
   private int midEndPage; // 中间要显示的页的结束页码
   private int firstPageNumber = 1; // 首页页码
   private int lastPageNumber = 1; // 最后一页页码
   
   private boolean firstPage;//是否是第一页
   private boolean lastPage;//是否是最后一页
   private int nextPage; // 下一页的页码
   private int prePage; // 前一页的页码
   
// private Sort sort;

   /**
    * 计算分页各项的值
    */
   public void cal() {
      //1
      firstPageNumber = 1;
      
      lastPageNumber = (int) ((totalCount - 1) / pageSize + 1); // (int) (totalCount / (pageSize < 1 ? 20 : pageSize) + firstPageNumber - 1);

//    if (totalCount % pageSize != 0 || lastPageNumber == 0) {
//       lastPageNumber++;
//    }

      if (lastPageNumber < firstPageNumber) {
         lastPageNumber = firstPageNumber;
      }

      if (pageNumber <= 1) {
         pageNumber = firstPageNumber;
         firstPage = true;
      }

      if (pageNumber >= lastPageNumber) {
         pageNumber = lastPageNumber;
         lastPage = true;
      }

      if (pageNumber < lastPageNumber - 1) {
         nextPage = pageNumber + 1;
      } else {
         nextPage = lastPageNumber;
      }

      if (pageNumber > 1) {
         prePage = pageNumber - 1;
      } else {
         prePage = firstPageNumber;
      }

      // 2
      if (pageNumber < firstPageNumber) {// 如果当前页小于首页
         pageNumber = firstPageNumber;
      }
      if (pageNumber > lastPageNumber) {// 如果当前页大于尾页
         pageNumber = lastPageNumber;
      }
      
      // 计算中间要显示的页索引范围
      midBeginPage = pageNumber - (midLength / 2);
      if (midBeginPage < firstPageNumber) {
         midBeginPage = firstPageNumber;
      }
      midEndPage = midBeginPage + midLength - 1;
      if (midEndPage >= lastPageNumber) {
         midEndPage = lastPageNumber;
         midBeginPage = midEndPage - midLength + 1;
         if (midBeginPage < firstPageNumber) {
            midBeginPage = firstPageNumber;
         }
      }
      
   }

// public int getOffset() {
//    return offset;
// }
//
// public void setOffset(int offset) {
//    this.offset = offset;
// }

   public int getPageNumber() {
      return pageNumber;
   }

   public void setPageNumber(int pageNumber) {
      if(pageNumber > 0) {
         this.pageNumber = pageNumber;
      }
   }

   public int getPageSize() {
      return pageSize;
   }

   public void setPageSize(int pageSize) {
      if(pageSize > 0) {
         this.pageSize = pageSize;
      }
   }

   public long getTotalCount() {
      return totalCount;
   }

   public void setTotalCount(long totalCount) {
      this.totalCount = totalCount;
   }

// public long getPageCount() {
//    return pageCount;
// }
//
// public void setPageCount(int pageCount) {
//    this.pageCount = pageCount;
// }

   public int getSlider() {
      return slider;
   }

   public void setSlider(int slider) {
      this.slider = slider;
   }

   public int getMidLength() {
      return midLength;
   }

   public void setMidLength(int midLength) {
      this.midLength = midLength;
   }

   public int getFirstPageNumber() {
      return firstPageNumber;
   }

   public void setFirstPageNumber(int firstPageNumber) {
      this.firstPageNumber = firstPageNumber;
   }

   public int getLastPageNumber() {
      return lastPageNumber;
   }

   public void setLastPageNumber(int lastPageNumber) {
      this.lastPageNumber = lastPageNumber;
   }

   public boolean isFirstPage() {
      return firstPage;
   }

   public void setFirstPage(boolean firstPage) {
      this.firstPage = firstPage;
   }

   public boolean isLastPage() {
      return lastPage;
   }

   public void setLastPage(boolean lastPage) {
      this.lastPage = lastPage;
   }

   public int getNextPage() {
      return nextPage;
   }

   public void setNextPage(int nextPage) {
      this.nextPage = nextPage;
   }

   public int getPrePage() {
      return prePage;
   }

   public void setPrePage(int prePage) {
      this.prePage = prePage;
   }

   public int getMidBeginPage() {
      return midBeginPage;
   }

   public void setMidBeginPage(int midBeginPage) {
      this.midBeginPage = midBeginPage;
   }

   public int getMidEndPage() {
      return midEndPage;
   }

   public void setMidEndPage(int midEndPage) {
      this.midEndPage = midEndPage;
   }

// public Sort getSort() {
//    return sort;
// }
//
// public void setSort(Sort sort) {
//    this.sort = sort;
// }

   public String toSql() {
      return " LIMIT #{pageSize} OFFSET (#{pageNo} - 1) * #{pageSize}";
   }

}
```



# es 相关

## es 对应的pom文件

```xml
<!-- es客户端 -->
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>transport</artifactId>
    <version>5.6.10</version>
</dependency>
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>x-pack-transport</artifactId>
    <version>5.6.1</version>
</dependency>
```

## es 工具类  ESHelper

```java
 
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ESHelper implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ESHelper.class);

    private TransportClient client;

    @Value("${es.clustername}")
    private String clustername;
    @Value("${es.hosts}")
    private String hosts;
    @Value("${es.username}")
    private String username;
    @Value("${es.password}")
    private String password;

    @Override
    public void destroy() throws Exception {
        logger.info("---bulkProcessor awaitClose---");
        if (client != null) {
            client.close();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        logger.info("es.cluster.name:" + clustername);
        logger.info("es.hosts:" + hosts);
        logger.info("es.username:" + username);
        logger.info("es.password:" + password);
        Settings settings = Settings.builder()
                .put("cluster.name", clustername)
//                .put("xpack.security.transport.ssl.enabled", false)
//                .put("xpack.security.user", username + ":" + password)
//                //.put("client.transport.sniff", true)// 自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
//                .put("transport.type", "security4")
//                .put("http.type", "security4")
                .build();

        //client = new PreBuiltTransportClient(settings);
        client = new PreBuiltXPackTransportClient(settings);

        String[] nodes = hosts.split(",");
        for (String node : nodes) {
            if (node.length() > 0) {// 跳过为空的node（当开头、结尾有逗号或多个连续逗号时会出现空node）
                String[] hostPort = node.split(":");
                try {
                    client.addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName(hostPort[0]), Integer.parseInt(hostPort[1])));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public TransportClient getClient() {
        return client;
    }

    public void setClient(TransportClient client) {
        this.client = client;
    }

    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取集群的状态
     */
    public List<DiscoveryNode> getClusterInfo() {
        List<DiscoveryNode> nodes = client.connectedNodes();
        return nodes;
    }

    /**
     * 新建索引
     *
     * @param indexName 索引名
     */
    public boolean createIndex(String indexName) {
        boolean isExist = false;

        isExist = indexExist(indexName);

        if (isExist)
            return false;

        CreateIndexResponse cIndexResponse = client.admin().indices()
                .create(new CreateIndexRequest(indexName))
                .actionGet();
        return cIndexResponse.isAcknowledged();
    }

    /**
     * 功能描述：验证索引是否存在
     *
     * @param index 索引名
     */
    public boolean indexExist(String index) {
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(index);
        IndicesExistsResponse inExistsResponse = client.admin().indices()
                .exists(inExistsRequest).actionGet();
        return inExistsResponse.isExists();
    }

    /**
     * 获取所有的索引
     */
    public String[] getAllIndex() {
        ClusterStateResponse response = client.admin().cluster().prepareState().execute().actionGet();
        //获取所有索引
        return response.getState().getMetaData().getConcreteAllIndices();
    }

    /**
     * 添加文档
     */
    public void putDoc(String indexname, String type, String json) throws Exception {
        IndexResponse response = client.prepareIndex(indexname, type).setSource(json, XContentType.JSON).get();
    }

    /**
     * 添加文档
     */
    public int putDoc(String indexname, String type, Map<String, Object> map) throws Exception {
        return client.prepareIndex(indexname, type).setSource(map).get().status().getStatus();
    }

    /**
     * 添加或更新文档
     */
    public void UpsetDoc(String indexname, String type, String id, Map<String, Object> map) throws Exception {
        IndexRequest indexRequest = new IndexRequest(indexname, type, id);
        indexRequest.source(map);

        UpdateRequest updateRequest = new UpdateRequest(indexname, type, id);
        updateRequest.doc(map).upsert(indexRequest);

        client.update(updateRequest).get();
    }

    /**
     * 添加或更新文档
     */
    public void UpsetDoc(String indexname, String type, String id, Map<String, Object> map,long timeout) throws Exception {
        IndexRequest indexRequest = new IndexRequest(indexname, type, id);
        indexRequest.source(map);

        UpdateRequest updateRequest = new UpdateRequest(indexname, type, id);
        updateRequest.doc(map).upsert(indexRequest);

        client.update(updateRequest).get(timeout, TimeUnit.MILLISECONDS);
    }


    /**
     * 根据id获取文档
     */
    public GetResponse getDoc(String index, String type, String id) {
        return client.prepareGet(index, type, id).get();
    }

    /**
     * 查询工具类
     */
    public SearchResponse searchDoc(String index, String type, QueryBuilder query, AggregationBuilder agg, Integer size, Integer from, Map<String, SortOrder> sortMap) {

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);

        if (query != null) {
            searchRequestBuilder.setQuery(query);
        }

        if (agg != null) {
            searchRequestBuilder.addAggregation(agg);
        }

        if (size != null)
            searchRequestBuilder.setSize(size);

        if (from != null)
            searchRequestBuilder.setFrom(from);


        if (sortMap != null && sortMap.size() > 0) {
            for (String key : sortMap.keySet()) {
                searchRequestBuilder.addSort(key, sortMap.get(key));
            }
        }

        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return response;
    }
}

```



## es 查询例子 ： 查询列表

```java
public void getEsDataList(SearchVo listVo) {
     // 1。  getIndexName  获取 索引（相当于 表 ）
    Date beginTm = listVo.getSearch().getCreateTimeBegin();
    Date endTm = listVo.getSearch().getCreateTimeEnd();
    String[] array = getIndexName(beginTm, endTm, dataSourceId);
    //2.根据索引，通过es客户预编译查询， 创建 关键搜索请求对象 并设置type ( type 相当于 数据库中 scheme  通常都是 doc ) 
    SearchRequestBuilder searchRequestBuilder = esHelper.getClient().prepareSearch(array).setTypes("doc");
	//3。 设置搜索条件
    searchRequestBuilder.setQuery(getQuery(listVo));
    //3.1 分页
    int size = listVo.getPaging().getPageSize();
    int pageNumber = listVo.getPaging().getPageNumber();
    searchRequestBuilder.setSize(size);
    if (pageNumber == 1) {
        searchRequestBuilder.setFrom(0);
    } else {
        pageNumber = pageNumber - 1;
        searchRequestBuilder.setFrom((pageNumber * size));//es 下标从0 开始 因此 不用 +1
    }
    if (listVo.getPaging().getPageSize() == 0) {
        searchRequestBuilder.setSize(1000);
    }
    //3.3 排序 
    searchRequestBuilder.addSort("createTime", SortOrder.DESC);
    //4 设置 需要返回的内容  （没设置 相当于*）

    //5 开始请求
    SearchResponse response = searchRequestBuilder.execute().actionGet();
    //6.处理请求返回的 数据
    if (response.getHits().getHits().length > 0) {
        List<TargetObject> list = new ArrayList<>();
        Long total = response.getHits().totalHits;
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> searchMap = hit.getSource();//获取的 是map
            String mapStr = JSON.toJSONString(searchMap); //转换成jsonString
            //在通过jsonString转成目标对象
            TargetObject info = JSONObject.parseObject(mapStr, TargetObject.class);
            list.add(info);
        }
        listVo.setResult(list);
        listVo.getPaging().setTotalCount(total);
        if (listVo.getPaging().getPageSize() > 0) {
            //重新计算分页
            listVo.getPaging().cal();
        }
    } else {
        listVo.setResult(new ArrayList<GoldRecordInfo>());
        listVo.getPaging().setTotalCount(0);
        if (listVo.getPaging().getPageSize() > 0) {
            listVo.getPaging().cal();
        }
    }
}

private QueryBuilder getQuery(SearchVo listVo) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        Date beginTm = listVo.getSearch().getCreateTimeBegin();
        Date endTm = listVo.getSearch().getCreateTimeEnd();
 
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //根据时间查询
        QueryBuilder queryCreateTm = QueryBuilders.rangeQuery("createTime")
            .gte(format.format(beginTm))
            .lte(format.format(endTm));
        queryBuilder.filter().add(queryCreateTm);
 		
    	//根据字符串过滤
        String Username = listVo.getSearch().getUsername();
        if (!StringUtils.isEmpty(Username)) {
            QueryBuilder query = QueryBuilders.termQuery("username.keyword", Username);
            queryBuilder.filter().add(query);
        }
 		//long类型
        Long PlayerId = listVo.getSearch().getPlayerId();
        if (PlayerId != null) {
            QueryBuilder query = QueryBuilders.termQuery("playerId", PlayerId);
            queryBuilder.filter().add(query);
        }

        //in 查询  
        if (typeList2 != null && typeList2.size() > 0) {
            for (Integer typeI : typeList2) {
                queryBuilder.should(QueryBuilders.termQuery("type", typeI));
            }
        } 

        return queryBuilder;
    }

private String[] getIndexName(Date beginTm, Date endTm, int dbid, String indexPrefix) {
        StringBuilder sb = new StringBuilder();
        String indexname = indexPrefix + "_";
        //        if (dbid > 0) {
        //            indexname += dbid + "_";
        //        } else
        indexname += "*_";

        Set<String> list = new HashSet<>();

        String formatBegin = DateUtils.getYearMonth(beginTm);
        String formatEnd = DateUtils.getYearMonth(endTm);

        int begin = Integer.valueOf(formatBegin);
        int end = Integer.valueOf(formatEnd);

        int sub = end - begin;
        if (sub == 0) {
            list.add(indexName + formatBegin);
        } else if (sub == 1) {
            list.add(indexName + formatBegin);
            list.add(indexName + formatEnd);
        } else if (sub == 2) {
            list.add(indexName + formatBegin);
            list.add(indexName + (begin + 1));
            list.add(indexName + formatEnd);
        }

        for (String str : list) {
            if(!str.contains("*")) {
                boolean isExist = esHelper.indexExist(str);
                if (!isExist) {
                    list.remove(str);
                }
            }
        }

        if (list.size() == 0) {
            list.add(indexPrefix + "_*");
        }

        return list.toArray(new String[list.size()]);
    }
```

es查询例子：

```java
//唯一的累加
AggregationBuilder cardinality = AggregationBuilders.cardinality("countPlayerId").field("playerId").precisionThreshold(100000); 
//SUM 求和
AggregationBuilder sumGrade = AggregationBuilders.sum("sumGrade").field("grade");

QueryBuilder query1 = QueryBuilders.rangeQuery("type").gte(1).lte(1);// 
QueryBuilder query2 = QueryBuilders.rangeQuery("type").gte(2).lte(2);// 
AggregationBuilder groupGrade = AggregationBuilders.filters("group_grade",query1,query2).subAggregation(sumGrade);//按类型得分 分组

searchRequestBuilder.setQuery(queryBuilder);
searchRequestBuilder.setSize(0);
searchRequestBuilder
    .addAggregation(cardinality)
    .addAggregation(sumGrade)
    .addAggregation(groupGrade) ;

//对应的 结果处理
Cardinality countPlayerId = response.getAggregations().get("countPlayerId");
Sum sumGradeValue = response.getAggregations().get("sumGrade");
InternalFilters filters = response.getAggregations().get("group_grade");
for (int j = 0; j < filters.getBuckets().size(); j++) {
    Sum groupSum = filters.getBuckets().get(j).getAggregations().get("sumGrade");
    if(j == 0){
         groupSum.getValue();
    } else if(j == 1){
        groupSum.getValue();
    }
}
```

按时间分组统计


```java
public void getListForHourGroup(ListVo listVo) {
        Date beginTm = listVo.getSearch().getCreateTimeBegin();
        Date endTm = listVo.getSearch().getCreateTimeEnd();

        if (beginTm == null || endTm == null) {
            return listVo;
        }
 
        String[] array = getIndexName(beginTm, endTm, dataSourceId);
     
        String dateTimeFormatStr = "yyyy-MM-dd HH";
        DateHistogramInterval dateHistogramInterval = DateHistogramInterval.HOUR;
        if ("5min".equals(listVo.getSearch().getGroupType())){
            dateTimeFormatStr = "yyyy-MM-dd HH:mm";//按5分钟分组
            dateHistogramInterval = DateHistogramInterval.minutes(5);
        } else if ("10min".equals(listVo.getSearch().getGroupType())){
            dateTimeFormatStr = "yyyy-MM-dd HH:mm";//按10分钟分组
            dateHistogramInterval = DateHistogramInterval.minutes(10);
        }else if ("15min".equals(listVo.getSearch().getGroupType())){
            dateTimeFormatStr = "yyyy-MM-dd HH:mm";//按15分钟分组
            dateHistogramInterval = DateHistogramInterval.minutes(15);
        } else if ("20min".equals(listVo.getSearch().getGroupType())){
            dateTimeFormatStr = "yyyy-MM-dd HH:mm";//按20分钟分组
            dateHistogramInterval = DateHistogramInterval.minutes(20);
        }else if ("30min".equals(listVo.getSearch().getGroupType())){
            dateTimeFormatStr = "yyyy-MM-dd HH:mm";//按30分钟分组
            dateHistogramInterval = DateHistogramInterval.minutes(30);
        }
         

        SearchRequestBuilder searchRequestBuilder = esHelper.getClient().prepareSearch(array).setTypes("doc");
        searchRequestBuilder.setSize(0);
        searchRequestBuilder.setQuery(getSummaryQuery(listVo));

        AggregationBuilder timeGroup =
                AggregationBuilders.dateHistogram("group")
                        .field("endTime")
                        .format(dateTimeFormatStr)
                        .dateHistogramInterval(dateHistogramInterval)
                        .minDocCount(0)
                        .extendedBounds(new ExtendedBounds(
                                DateUtils.getFormatDate(dateTimeFormatStr, beginTm),
                                DateUtils.getFormatDate(dateTimeFormatStr, endTm)));

        AggregationBuilder cardinality = AggregationBuilders.cardinality("countPlayerId").field("playerId").precisionThreshold(100000);// 玩家数
        AggregationBuilder count1 = AggregationBuilders.count("count1").field("gameId");//  
        AggregationBuilder sum1 = AggregationBuilders.sum("sum1").field("grade"); 
        timeGroup.subAggregation(cardinality);
        timeGroup.subAggregation(count1);
        timeGroup.subAggregation(sum1);

        searchRequestBuilder.addAggregation(timeGroup);
        //查询
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        if ( response.getAggregations() != null){
            InternalDateHistogram dateHistogram = response.getAggregations().get("group");
            if (dateHistogram.getBuckets().size() > 0) {
                List<GoldRecordInfoStatDay> list = new ArrayList<>();
                for (InternalDateHistogram.Bucket dBucket : dateHistogram.getBuckets()) {

                    String Tm = dBucket.getKeyAsString();
                    Cardinality countPlayerId = dBucket.getAggregations().get("countPlayerId");
                    ValueCount count1Value = dBucket.getAggregations().get("count1");
                    Sum sum1Value = dBucket.getAggregations().get("sum1");

                    ResultObject result = new ResultObject();
                    result.setPlayerNum(Long.valueOf(countPlayerId.getValue()).intValue());
                    result.setCount(count1Value.getValue());
                    result.setSumNum(sum1Value.getValue());
                    result.setCreateTime(DateTool.parseDate(Tm, dateTimeFormatStr));

                    list.add(result);
                }
                listVo.setResult(list);
            }
        }

        return listVo;
    }
```

