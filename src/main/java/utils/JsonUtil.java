package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * JsonUtil - Utility class for JSON operations
 * Provides methods for JSON parsing and generation
 */
public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Convert object to JSON string
     */
    public static String objectToJson(Object object) {
        try {
            String jsonString = gson.toJson(object);
            LoggerUtil.info("Object converted to JSON");
            return jsonString;
        } catch (Exception e) {
            LoggerUtil.error("Failed to convert object to JSON", e);
            return "";
        }
    }

    /**
     * Convert JSON string to object
     */
    public static <T> T jsonToObject(String jsonString, Class<T> classType) {
        try {
            T object = gson.fromJson(jsonString, classType);
            LoggerUtil.info("JSON converted to object");
            return object;
        } catch (Exception e) {
            LoggerUtil.error("Failed to convert JSON to object", e);
            return null;
        }
    }

    /**
     * Pretty print JSON
     */
    public static String prettyPrintJson(String jsonString) {
        try {
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
            String prettyJson = gson.toJson(jsonObject);
            LoggerUtil.info("JSON pretty printed");
            return prettyJson;
        } catch (Exception e) {
            LoggerUtil.error("Failed to pretty print JSON", e);
            return jsonString;
        }
    }

    /**
     * Get value from JSON by key path
     */
    public static Object getValueFromJson(String jsonString, String key) {
        try {
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
            LoggerUtil.info("Retrieved value from JSON with key: " + key);
            return jsonObject.get(key);
        } catch (Exception e) {
            LoggerUtil.error("Failed to get value from JSON", e);
            return null;
        }
    }
}

