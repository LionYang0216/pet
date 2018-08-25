/*
 * Copyright 2016 Eveoh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gzsoftware.pet.entity.vo;


import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.gzsoftware.pet.utils.CommonUtil;
import com.gzsoftware.pet.utils.MapBeanUtil;

/**
 * Data Tables Request
 * @author Pango Leung
 */
@JsonIgnoreProperties(ignoreUnknown = true)//忽略未知属性  
public class DataTablesRequest {

	private static Log log = LogFactory.getLog(DataTablesRequest.class);
	
    private int draw;
    private int start;
    private int length;
    private DataTablesSearch search;
    private List<DataTablesColumn> columns = Collections.emptyList();
    private List<DataTablesOrder> order = Collections.emptyList();
    private Map condition;
    
    private String orderBySql;
    
   

    /**
     * get the condition entity: admin , product... from UI json
     * @param type
     * @return
     */
    public Object getConditionEntity(Class<?> type){
    	
    	if(getCondition()==null){
    		return null;
    	}
    	
    	Object obj = null;
    	try{
    		obj = MapBeanUtil.toBean(type,getCondition());
    	}catch(Exception ex){
    		log.error(ex);
    	}
    	return obj;
    }
    
    
    public Map getCondition() {
		return condition;
	}

	public void setCondition(Map condition) {
		this.condition = condition;
	}

	public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public DataTablesSearch getSearch() {
        return search;
    }

    public void setSearch(DataTablesSearch search) {
        this.search = search;
    }

    public List<DataTablesColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DataTablesColumn> columns) {
        this.columns = columns;
    }

    public List<DataTablesOrder> getOrder() {
        return order;
    }

    public void setOrder(List<DataTablesOrder> order) {
        this.order = order;
    }

    /**
     * 生成 order by 的子句
     * @return
     */
	public String getOrderBySql() {
		if(this.order.size()>0) {
			int i= this.order.get(0).getColumn(); // 获取当前指定排序的是第几个字段
			String orderJavaColumn = this.getColumns().get(i).getData();		// 从columns里面，根据index获取要排序的字段名
			String orderDBColumn = CommonUtil.camelToUnderline(orderJavaColumn).toUpperCase(); // 把形如 adminName 转换为 ADMIN_NAME
			String retSql = " " + orderDBColumn +  " " + this.order.get(0).getDir()+" "; // 构造形如 order by admin_name desc 的样子
			//log.info(retSql);
			return retSql; 
		}else{
			return "  ID desc "; // 如果没指定，默认按照ID排序，整个系统所有表都是有ID
		}
		
	}


	
}
@JsonIgnoreProperties(ignoreUnknown = true)//忽略未知属性  
class DataTablesColumn {

    private String name;
    private String data;
  

	private boolean searchable;
    private boolean orderable;
    private DataTablesSearch search;

    public String getData() {
  		return data;
  	}

  	public void setData(String data) {
  		this.data = data;
  	}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public boolean isOrderable() {
        return orderable;
    }

    public void setOrderable(boolean orderable) {
        this.orderable = orderable;
    }

    public DataTablesSearch getSearch() {
        return search;
    }

    public void setSearch(DataTablesSearch search) {
        this.search = search;
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)//忽略未知属性  
class DataTablesOrder {

    public enum Direction {
        asc, desc
    }

    private int column;
    private Direction dir;


    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)//忽略未知属性  
class DataTablesSearch {

    private String value;
    private boolean regex;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRegex() {
        return regex;
    }

    public void setRegex(boolean regex) {
        this.regex = regex;
    }
}
