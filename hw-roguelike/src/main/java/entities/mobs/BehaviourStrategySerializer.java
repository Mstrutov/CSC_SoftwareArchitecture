package entities.mobs;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BehaviourStrategySerializer implements JsonSerializer<BehaviourStrategy> {
    @Override
    public JsonElement serialize(BehaviourStrategy src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getClass().getName());
    }
}
