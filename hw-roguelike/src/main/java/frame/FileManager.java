package frame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.mobs.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileManager {
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(BehaviourStrategy.class, new BehaviourStrategySerializer())
            .registerTypeAdapter(BehaviourStrategy.class, new BehaviourStrategyDeserializer())
            .registerTypeAdapter(AggressiveBehaviourStrategy.class, new BehaviourStrategySerializer())
            .registerTypeAdapter(AggressiveBehaviourStrategy.class, new BehaviourStrategyDeserializer())
            .registerTypeAdapter(PassiveBehaviourStrategy.class, new BehaviourStrategySerializer())
            .registerTypeAdapter(PassiveBehaviourStrategy.class, new BehaviourStrategyDeserializer())
            .registerTypeAdapter(CowardBehaviourStrategy.class, new BehaviourStrategySerializer())
            .registerTypeAdapter(CowardBehaviourStrategy.class, new BehaviourStrategyDeserializer())
            .registerTypeAdapterFactory(new MobAdapterFactory(DefaultMob.class))
            .create();

    public void writeMap(Map<Integer, Map<Integer, Frame>> frames) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("map.json"));
            writer.write(gson.toJson(frames));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
