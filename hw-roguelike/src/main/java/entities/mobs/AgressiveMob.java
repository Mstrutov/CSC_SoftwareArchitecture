package entities.mobs;

import entities.Player;
import frame.CollisionController;
import frame.Frame;

public class AgressiveMob extends DefaultMob{

    private int moveCountdown = 25;

    public AgressiveMob(int coordX, int coordY, int power) {
        super(coordX, coordY, power);
    }

    @Override
    public boolean action(Player player, Frame frame) {
        if (moveCountdown > 0) {
            moveCountdown--;
        } else {
            int directionX = Math.abs(getCoordX() - player.getCoordX()) == 0 ? 0 : (getCoordX() - player.getCoordX()) / Math.abs(getCoordX() - player.getCoordX());
            int directionY = Math.abs(getCoordY() - player.getCoordY()) == 0 ? 0 : (getCoordY() - player.getCoordY()) / Math.abs(getCoordY() - player.getCoordY());
            if (CollisionController.isOkToMoveForMob(frame, getCoordX() - directionX, getCoordY() - directionY)) {
                super.setCoordX(getCoordX() - directionX);
                super.setCoordY(getCoordY() - directionY);
            } else {
                if (CollisionController.isOkToMoveForMob(frame, getCoordX() - directionX, getCoordY())) {
                    super.setCoordX(getCoordX() - directionX);
                    if (CollisionController.isOkToMoveForMob(frame, getCoordX(), getCoordY() - directionY)) {
                        super.setCoordY(getCoordY() - directionY);
                    }
                } else if (CollisionController.isOkToMoveForMob(frame, getCoordX(), getCoordY() - directionY)) {
                    super.setCoordY(getCoordY() - directionY);
                    if (CollisionController.isOkToMoveForMob(frame, getCoordX() - directionX, getCoordY())) {
                        super.setCoordX(getCoordX() - directionX);
                    }
                }
            }
            moveCountdown = 25;
        }
        return super.action(player, frame);
    }
}
