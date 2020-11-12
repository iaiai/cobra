package com.iaiai.cobra.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
public class JsonUtil {
    /**
     * 核心Mapper
     */
    private final ObjectMapper mapper;

    private JsonUtil() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 序列化对象
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public String serialize(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    /**
     * 反序列化 Class
     * @param json json字符串
     * @param clazz 目标Class
     * @param <T>
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public <T> T deserialize(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(json, clazz);
    }

    /**
     * 反序列化List
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public <T> List<T> deserializeForList(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        return mapper.readValue(json, javaType);
    }

    public ObjectMapper getMapper(){
        return this.mapper;
    }

    public static JsonUtil getInstance() {
        return SingletonModel.jsonUtil;
    }

    private static class SingletonModel {
        private static JsonUtil jsonUtil = new JsonUtil();
    }

//    public <T> Object deserialize(String json, Class<T> type, Parser<T> parser) throws Exception {
//        T obj = mapper.readValue(json, type);
//        Object result = parser.testReflect(obj);
//        return result;
//    }
//
//    public interface Parser<T> {
//        Object testReflect(T obj);
//    }

}
