package com.tool.wxtoolproject.api.common.util.json;

import com.alibaba.fastjson.serializer.ValueFilter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhaolei
 * fastJson 序列化对象转换器
 * 空对象转换器(慎用, 通过此方法序列化的数数据不能反序列化)
 */
public class ValueToEmptyFilter implements ValueFilter {

	@Override
	public Object process(Object object, String name, Object value) {
		if (value == null) {
			return "";
		}
		if (value instanceof Date) {
			value = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(value);
		}
		return value;
	}

}
