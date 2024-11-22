import com.example.tictactoes.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();
        model.resetGame();  // Resets the game state
    }

    @Test
    void testValidMove() {
        // First move is valid
        boolean firstMove = model.makeMove(0);
        assertTrue(firstMove, "First move should be valid.");

        // Attempt to overwrite the same cell with another move
        boolean secondMove = model.makeMove(0);
        assertFalse(secondMove, "Second move should be invalid as cell is already occupied.");
        assertEquals("X", model.getBoard()[0], "Cell should remain 'X' if already occupied.");
    }

    @Test
    void testRoundCompletionWithWin() {
        // Simulate a winning condition
        model.makeMove(0);
        model.makeMove(1);
        model.makeMove(2);

        assertTrue(model.checkWinner(), "A winner should be detected when three cells in a row match.");
        assertFalse(model.isGameActive(), "Game should end when a winner is detected.");
    }

    @Test
    void testRoundCompletionWithDraw() {
        // Simulate a full board with no winner
        model.setBoard(new String[] {"X", "O", "X", "X", "O", "O", "O", "X", "X"});

        assertTrue(model.isBoardFull(), "The board should be full with no empty spaces.");
        assertFalse(model.checkWinner(), "There should be no winner in a draw.");
        assertFalse(model.isGameActive(), "Game should end when the board is full.");
    }

    @Test
    void testResetGameLogic() {
        // Set up a mid-game state
        model.makeMove(0);
        model.makeMove(1);

        model.resetGame();

        String[] board = model.getBoard();
        assertNotNull(board, "Board should be initialized.");
        for (String cell : board) {
            assertNull(cell, "All cells should be empty after a reset.");
        }
        assertEquals("X", model.getCurrentPlayer(), "Player X should start after a reset.");
        assertTrue(model.isGameActive(), "Game should be active after a reset.");
    }
}