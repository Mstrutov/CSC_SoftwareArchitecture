package frame;

import entities.Mob;
import entities.Obstacle;

public class CollisionController {
    public static boolean isObstacleCollidesWithObstacle(Obstacle a, Obstacle b) {
        return Math.max(a.getLeftBorder(), b.getLeftBorder()) < Math.min(a.getRightBorder(), b.getRightBorder())
                && Math.max(a.getTopBorder(), b.getTopBorder()) < Math.min(a.getBottomBorder(), b.getBottomBorder());
    }

    public static boolean isObstacleCollidesWithMob(Obstacle a, Mob mob) {
        return a.getLeftBorder() <= mob.getCoordX() && a.getRightBorder() >= mob.getCoordX()
                && a.getBottomBorder() <= mob.getCoordY() && a.getTopBorder() >= mob.getCoordY();
    }

    public static boolean isMobCollidesWithMob(Mob a, Mob b) {
        return a.getCoordX() == b.getCoordX() && a.getCoordY() == b.getCoordY();
    }
    
    public static boolean isOkToMove(Frame frame, int coordX, int coordY) {
        for (Obstacle a : frame.getObstacles()) {
            if (a.getLeftBorder() <= coordX && a.getRightBorder() >= coordX
                    && a.getBottomBorder() <= coordY && a.getTopBorder() >= coordY) {
                return false;
            }
        }

        for (Mob a : frame.getMobs()) {
            if (a.getCoordX() == coordX && a.getCoordY() == coordY) {
                return false;
            }
        }
        return true;
    }
}
