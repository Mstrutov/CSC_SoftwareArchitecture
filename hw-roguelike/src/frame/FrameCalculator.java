package frame;

import entities.Player;

public class FrameCalculator {
    Frame currentFrame;
    FrameGenerator frameGenerator = new FrameGenerator();
    Player player = new Player();


    public FrameCalculator(Frame startFrame) {
        currentFrame = startFrame;
    }

    public FrameCalculator() {
        currentFrame = frameGenerator.getNextFrame();
    }

    public Frame nextFrame() {

    }
}
