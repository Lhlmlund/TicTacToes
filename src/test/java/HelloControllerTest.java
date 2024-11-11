import com.example.tictactoes.HelloController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {

    private HelloController controller;

    @BeforeEach
    void setUp() {
        controller = new HelloController();
        controller.resetGameLogic();  // Only reset logic, not UI
    }

    @Test
    void testValidMove() {
        // First move is valid
        boolean firstMove = controller.makeMove(0, "X");
        assertTrue(firstMove, "First move should be valid.");

        // Attempt to overwrite the same cell with another move
        boolean secondMove = controller.makeMove(0, "O");
        assertFalse(secondMove, "Second move should be invalid as cell is already occupied.");
        assertEquals("X", controller.board[0], "Cell should remain 'X' if already occupied.");
    }

    @Test
    void testRoundCompletionWithWin() {
        controller.board[0] = "X";
        controller.board[1] = "X";
        controller.board[2] = "X";

        assertTrue(controller.checkWinner(), "A winner should be detected when three cells in a row match");
    }

    @Test
    void testRoundCompletionWithDraw() {
        controller.board = new String[] {"X", "O", "X", "X", "O", "O", "O", "X", "X"};

        assertTrue(controller.isBoardFull(), "The board should be full with no empty spaces");
        assertFalse(controller.checkWinner(), "There should be no winner in a draw");
    }
}
