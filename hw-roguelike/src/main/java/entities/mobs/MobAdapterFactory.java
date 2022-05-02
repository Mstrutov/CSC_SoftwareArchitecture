package entities.mobs;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class MobAdapterFactory implements TypeAdapterFactory {
    private final Class<? extends Mob> implementationClass;

    public MobAdapterFactory(Class<? extends Mob> implementationClass) {
        this.implementationClass = implementationClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!Mob.class.equals(type.getRawType())) return null;

        return (TypeAdapter<T>) gson.getAdapter(implementationClass);
    }
}