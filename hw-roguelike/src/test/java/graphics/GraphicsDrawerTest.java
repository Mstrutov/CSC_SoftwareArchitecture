package graphics;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import entities.Obstacle;
import entities.mobs.Mob;
import entities.mobs.PassiveMob;
import frame.Frame;
import frame.FrameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// TODO: perhaps it is better to check screen final state, not the whole drawer-screen interaction process
public class GraphicsDrawerTest {

    Screen screen;
    @Captor
    ArgumentCaptor<Integer> columnCaptor;
    @Captor
    ArgumentCaptor<Integer> rowCaptor;
    @Captor
    ArgumentCaptor<TextCharacter> characterCaptor;

    private GraphicsDrawer graphicsDrawer;

    @BeforeEach
    void init() {
        screen = mock(Screen.class);
        MockitoAnnotations.openMocks(this);

        graphicsDrawer = new GraphicsDrawer(screen);
    }

    @Test
    void clearsScreenOnInit() {
        verify(screen, times( FrameGenerator.PLAYGROUND_WIDTH * FrameGenerator.PLAYGROUND_HEIGHT))
                .setCharacter(columnCaptor.capture(), rowCaptor.capture(), characterCaptor.capture());

        List<TextCharacter> chars = characterCaptor.getAllValues();
        for (TextCharacter character : chars) {
            assertEquals(emptyCharacter(), character);
        }
    }


    @Test
    void drawsAliveMobAndSingleObstacle() {
        Obstacle obstacle = new Obstacle(10, 10, 10, 10);
        Mob mob = new PassiveMob(15, 15, 3);

        Frame frame = new Frame(List.of(obstacle), List.of(mob));
        graphicsDrawer.draw(frame);

        int numClearingScreenOps = FrameGenerator.PLAYGROUND_WIDTH * FrameGenerator.PLAYGROUND_HEIGHT * 2;
        verify(screen, times( numClearingScreenOps + 2))
                .setCharacter(columnCaptor.capture(), rowCaptor.capture(), characterCaptor.capture());

        List<Integer> columns = columnCaptor.getAllValues();
        List<Integer> rows = rowCaptor.getAllValues();
        List<TextCharacter> chars = characterCaptor.getAllValues();

        assertEquals(obstacle.getLeftBorder(), columns.get(numClearingScreenOps));
        assertEquals(obstacle.getBottomBorder(), rows.get(numClearingScreenOps));
        assertEquals(obstacleCharacter(obstacle), chars.get(numClearingScreenOps));

        assertEquals(mob.getCoordX(), columns.get(numClearingScreenOps + 1));
        assertEquals(mob.getCoordY(), rows.get(numClearingScreenOps + 1));
        assertEquals(mobCharacter(mob), chars.get(numClearingScreenOps + 1));

    }

    private static TextCharacter mobCharacter(Mob mob) {
        return TextCharacter.fromCharacter(
                GraphicsDrawer.CHAR_OF.MOB.get(),
                mob.isDead() ? TextColor.ANSI.RED : TextColor.ANSI.DEFAULT,
                TextColor.ANSI.DEFAULT)[0];
    }
    private static TextCharacter obstacleCharacter(Obstacle obstacle) {
        return TextCharacter.fromCharacter(
                GraphicsDrawer.CHAR_OF.OBSTACLE.get(),
                TextColor.ANSI.DEFAULT,
                TextColor.ANSI.DEFAULT)[0];
    }
    private static TextCharacter emptyCharacter() {
        return TextCharacter.fromCharacter(
                GraphicsDrawer.CHAR_OF.BLANK.get(),
                TextColor.ANSI.DEFAULT,
                TextColor.ANSI.DEFAULT)[0];
    }
}
