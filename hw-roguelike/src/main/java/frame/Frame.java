package frame;

import entities.Obstacle;
import entities.mobs.Mob;
import entities.player.Player;

import java.util.List;

public class Frame {
    private final List<Obstacle> obstacles;
    private final List<Mob> mobs;
    private Player player = null;

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

    public void addPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
