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
            int directionX = Math.abs(ofMob.getCoordX() - toPlayer.getCoordX()) == 0 ? 0 :
                    (ofMob.getCoordX() - toPlayer.getCoordX()) / Math.abs(ofMob.getCoordX() - toPlayer.getCoordX());
            int directionY = Math.abs(ofMob.getCoordY() - toPlayer.getCoordY()) == 0 ? 0 :
                    (ofMob.getCoordY() - toPlayer.getCoordY()) / Math.abs(ofMob.getCoordY() - toPlayer.getCoordY());
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
