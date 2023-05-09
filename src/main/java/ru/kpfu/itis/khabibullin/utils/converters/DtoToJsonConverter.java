package ru.kpfu.itis.khabibullin.utils.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author Khabibullin Alisher
 */

public class DtoToJsonConverter {
    public static <T> String toJsonString(T obj) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Object value = field.get(obj);
            sb.append("\"").append(field.getName()).append("\":");
            if (value == null) {
                sb.append("null");
            }  else if (value instanceof Enum) {
                sb.append("\"").append(((Enum<?>) value).name()).append("\"");
            } else if (value instanceof String) {
                sb.append("\"").append(value).append("\"");
            } else if (value instanceof Number || value instanceof Boolean) {
                sb.append(value);
            } else if (value instanceof LocalDateTime dateTime) {
                sb.append("\"").append(dateTime.truncatedTo(ChronoUnit.SECONDS)).append("\"");
            } else if (value instanceof List<?> list) {
                sb.append("[");
                for (int j = 0; j < list.size(); j++) {
                    sb.append(toJsonString(list.get(j)));
                    if (j < list.size() - 1) {
                        sb.append(",");
                    }
                }
                sb.append("]");
            } else {
                if (value.getClass() == obj.getClass()) {
                    sb.append("{}");
                } else {
                    sb.append(toJsonString(value));
                }
            }
            if (i < fields.length - 1) {
                sb.append(",");
            }
            field.setAccessible(false);
        }
        sb.append("}");
        return sb.toString();
    }

    public static <T> String toJsonString(List<T> objects) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < objects.size(); i++) {
            Object obj = objects.get(i);
            sb.append(toJsonString(obj));
            if (i < objects.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }


    public static <T> String toJsonStringUsingToString(T object) throws JsonProcessingException, IllegalAccessException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(toJsonString(object));
    }

    public static <T> String toJsonStringUsingToString(List<T> objects) throws JsonProcessingException, IllegalAccessException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(toJsonString(objects));
    }
}
