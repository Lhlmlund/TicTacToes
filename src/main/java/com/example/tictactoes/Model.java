package com.example.tictactoes;

public class Model {

    // Instance variables

    public String[] board;
    public String currentPlayer;
    private boolean gameActive;
    public int winCounterPlayer;
    public int winCounterComputer;



public Model() {

    board = new String[9];
    resetGame();
    winCounterPlayer = 0;
    winCounterComputer = 0;

}

// Getters

public String[] getBoard() {return board;}
public String getCurrentPlayer() { return currentPlayer;}
public boolean isGameActive() {return gameActive;}
public int getWinCounterPlayer() {return winCounterPlayer;}
public int getWinCounterComputer() {return winCounterComputer;}

// Reset's the game, sets active player to player (X)

public void resetGame() {
    currentPlayer = "X";
    gameActive = true;
    for (int i = 0; i < 9; i++) {
        board[i] = null;

    }
}


public void resetScores() {
    winCounterPlayer = 0;
    winCounterComputer = 0;
}




// set move if game active, if null else false

public boolean makeMove(int index) {
    if (board[index] == null && gameActive) {
        board[index] = currentPlayer;
        return true;  // Move was valid and executed
    }
    return false;  // Move was invalid
}


public void togglePlayer() {
    // ternary operator swap
    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
}

    // winning moves matrix
    public boolean checkWinner() {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Row win index
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Column win index
                {0, 4, 8}, {2, 4, 6}             // Diagonals win index
    };

    for (int[] condition : winConditions) {
        if (board[condition[0]] != null &&
                board[condition[0]].equals(board[condition[1]]) &&
                board[condition[1]].equals(board[condition[2]])) {
            gameActive = false;
            if (currentPlayer.equals("X")) {
                winCounterPlayer++;
            } else {
                winCounterComputer++;
            }
            return true;
        }
    }

    if (isBoardFull()) {
        gameActive = false;
    }


    return false;
}
    public boolean isBoardFull() {
        for (String cell : board) {
            if (cell == null) return false;
        }
        return true;
    }

    // Set the board for tests.

    public void setBoard(String[] newBoard) {
        if (newBoard != null && newBoard.length == 9) {
            this.board = newBoard;
        } else {
            throw new IllegalArgumentException("Board must have exactly 9 cells.");
        }
    }















}
