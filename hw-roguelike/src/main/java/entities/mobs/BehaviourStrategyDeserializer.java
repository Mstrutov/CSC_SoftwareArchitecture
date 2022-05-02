package entities.mobs;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

public class BehaviourStrategyDeserializer implements JsonDeserializer<BehaviourStrategy> {
    @Override
    public BehaviourStrategy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            Class behaviourStrategyClass = Class.forName(json.getAsString());
            Constructor behaviourStrategyConstructor = behaviourStrategyClass.getConstructor();
            return (BehaviourStrategy) behaviourStrategyConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
