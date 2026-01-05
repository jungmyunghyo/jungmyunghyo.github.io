import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
	private static final ObjectMapper OBJECT_MAPPER = getObjectMapper();
	
	private static final ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, true);
		objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false);
		objectMapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, false);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		return objectMapper;
	}
	public static final <TV> String parseDto(TV dto) {
		if (dto == null) {
			return null;
		}
		try {
			return OBJECT_MAPPER.writeValueAsString(dto);	
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException in parsing [parseDto] data.", e);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public static final <TV> TV cloneDto(TV dto) {
		if (dto == null) {
			return null;
		}
		return OBJECT_MAPPER.convertValue(dto, (Class<TV>) dto.getClass());
	}
	public static final JsonNode parse(String result) {
		if (result == null || result.isEmpty()) {
			return null;
		}
		try {
			return OBJECT_MAPPER.readTree(result);	
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException in parsing [parse] data.", e);
			return null;
		}
	}
	public static final <TV> List<TV> parse(JsonNode resultArray, Class<TV> clazz) {
		if (resultArray == null || !resultArray.isArray() || resultArray.isEmpty()) {
			return Collections.emptyList();
		}
		try {
			return OBJECT_MAPPER.readValue(resultArray.toString(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException in parsing [parse] data.", e);
		}
		List<TV> list = new ArrayList<>(resultArray.size());
		for (JsonNode result : resultArray) {
			try {
				list.add(OBJECT_MAPPER.treeToValue(result, clazz));
			} catch (JsonProcessingException e) {
				log.error("JsonProcessingException in parsing [parse] data.", e);
			}
		}
		return list;
	}
	public static final int getInt(JsonNode result, String key) {
		return hasKeys(result, key) ? result.get(key).asInt(0) : 0;
	}
	@SafeVarargs
	public static final boolean hasKeys(JsonNode result, String... keys) {
		return hasKeys(result, Arrays.asList(keys));
	}
	private static final boolean hasKeys(JsonNode result, List<String> keyList) {
		if (result == null || result.isEmpty() || keyList == null || keyList.isEmpty()) {
			return false;
		}
		for (String key : keyList) {
			if (!result.has(key)) {
				return false;
			}
		}
		return true;
	}
}