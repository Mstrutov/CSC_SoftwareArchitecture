package frame;

import entities.Mob;
import entities.Obstacle;

import java.util.List;

public class Frame {
    private final List<Obstacle> obstacles;
    private final List<Mob> mobs;

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Mob> getMobs() {
        return mobs;
    }

    public Frame(List<Obstacle> obstacles, List<Mob> mobs) {
        this.obstacles = obstacles;
        this.mobs = mobs;
    }
}
