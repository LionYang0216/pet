package com.gzsoftware.pet.entity.vo;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * JSON Object Mappers
 * @author pango leung
 *
 */
public class JsonObjectMapper extends ObjectMapper {

	public JsonObjectMapper() {
		// 目标类中找不到json字符串中属性时直接忽略
		this.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
