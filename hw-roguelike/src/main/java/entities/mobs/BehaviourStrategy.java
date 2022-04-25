package entities.mobs;

import entities.player.Player;
import frame.Frame;

public interface BehaviourStrategy {
    boolean action(Mob ofMob, Player toPlayer, Frame atFrame);
}
