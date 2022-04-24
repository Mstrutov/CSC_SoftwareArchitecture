package entities.mobs;

import entities.Player;
import frame.CollisionController;
import frame.Frame;

public class CowardBehaviourStrategy extends BehaviourStrategyImpl {
    private int moveCountdown = 25;
    
    @Override
    public boolean action(Mob ofMob, Player toPlayer, Frame atFrame) {
        if (moveCountdown > 0) {
            moveCountdown--;
        } else {
            int directionX = (int) Math.signum(ofMob.getCoordX() - toPlayer.getCoordX());
            int directionY = (int) Math.signum(ofMob.getCoordY() - toPlayer.getCoordY());
            if (CollisionController.isOkToMoveForMob(atFrame, ofMob.getCoordX() + directionX,
                    ofMob.getCoordY() + directionY)) {
                ofMob.setCoordX(ofMob.getCoordX() + directionX);
                ofMob.setCoordY(ofMob.getCoordY() + directionY);
            } else {
                if (CollisionController.isOkToMoveForMob(atFrame, ofMob.getCoordX() + directionX, ofMob.getCoordY())) {
                    ofMob.setCoordX(ofMob.getCoordX() + directionX);
                    if (CollisionController.isOkToMoveForMob(atFrame, ofMob.getCoordX(),
                            ofMob.getCoordY() + directionY)) {
                        ofMob.setCoordY(ofMob.getCoordY() + directionY);
                    }
                } else if (CollisionController.isOkToMoveForMob(atFrame, ofMob.getCoordX(),
                        ofMob.getCoordY() + directionY)) {
                    ofMob.setCoordY(ofMob.getCoordY() + directionY);
                    if (CollisionController.isOkToMoveForMob(atFrame, ofMob.getCoordX() + directionX,
                            ofMob.getCoordY())) {
                        ofMob.setCoordX(ofMob.getCoordX() + directionX);
                    }
                }
            }
            moveCountdown = 25;
        }
        return attackIfAble(ofMob, toPlayer, atFrame);
    }
}
