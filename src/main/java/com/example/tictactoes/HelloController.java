package com.example.tictactoes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

public class HelloController {
    @FXML
    private GridPane gameBoard;
    @FXML
    private Label winnerLabel;
    @FXML
    private Label playerWinsLabel;
    @FXML
    private Label computerWinsLabel;

    private String currentPlayer = "X";
    private int winCounterPlayer = 0;
    private int winCounterComputer = 0;
    private String[] board = new String[9];
    private boolean gameActive = true;




    @FXML
    public void initialize() {
        resetGame();
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        if (!gameActive) return;

        Button clickedButton = (Button) event.getSource();
        int index = GridPane.getRowIndex(clickedButton) * 3 + GridPane.getColumnIndex(clickedButton);

        if (board[index] == null) {
            board[index] = currentPlayer;
            clickedButton.setText(currentPlayer);

            if (checkWinner()) {
                gameActive = false;
                if (currentPlayer.equals("X")) {
                    winCounterPlayer++;
                    playerWinsLabel.setText(String.valueOf(winCounterPlayer));
                    winnerLabel.setText("Player Wins!");
                } else {
                    winCounterComputer++;
                    computerWinsLabel.setText(String.valueOf(winCounterComputer));
                    winnerLabel.setText("Computer Wins!");
                }
            } else if (isBoardFull()) {
                winnerLabel.setText("It's a Draw!");
                gameActive = false;
            } else {
                togglePlayer();
                if (currentPlayer.equals("O")) makeComputerMove(); // determine move order
            }
        }
    }

    private void togglePlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    private void makeComputerMove() {
        if (!gameActive) return;

        //Check for winning move
        Integer winningMove = findWinningOrBlockingMove("O");
        if (winningMove != null) {
            executeMove(winningMove);
            return;
        }

        // Block the player from winning
        Integer blockingMove = findWinningOrBlockingMove("X");
        if (blockingMove != null) {
            executeMove(blockingMove);
            return;
        }

        //Take the center if available
        if (board[4] == null) {
            executeMove(4);
            return;
        }

        //Take a corner if available
        int[] corners = {0, 2, 6, 8};
        for (int corner : corners) {
            if (board[corner] == null) {
                executeMove(corner);
                return;
            }
        }

        // Take any side if available
        int[] sides = {1, 3, 5, 7};
        for (int side : sides) {
            if (board[side] == null) {
                executeMove(side);
                return;
            }
        }
    }

    // Helper method to execute a move and check for a winner
    private void executeMove(int index) {
        board[index] = currentPlayer;
        Button button = (Button) gameBoard.getChildren().get(index);
        button.setText("O");

        if (checkWinner()) {
            winCounterComputer++;
            computerWinsLabel.setText(String.valueOf(winCounterComputer));
            winnerLabel.setText("Computer Wins!");
            gameActive = false;
        }
        togglePlayer();
    }

    // finding winning or blocking move for the specified player
    private Integer findWinningOrBlockingMove(String player) {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] condition : winConditions) {
            int count = 0;
            Integer emptyIndex = null;

            for (int i : condition) {
                if (board[i] != null && board[i].equals(player)) {
                    count++;
                } else if (board[i] == null) {
                    emptyIndex = i;
                }
            }

            // If two cells in a win condition are filled by the player and one is empty
            if (count == 2 && emptyIndex != null) {
                return emptyIndex;
            }
        }
        return null;
    }

    // winning moves indexes
    private boolean checkWinner() {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Row win index
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Column win index
                {0, 4, 8}, {2, 4, 6}             // Diagonals win index
        };

        for (int[] condition : winConditions) {
            if (board[condition[0]] != null &&
                    board[condition[0]].equals(board[condition[1]]) &&
                    board[condition[1]].equals(board[condition[2]])) {
                return true;
            }
        }
        return false;
    }


    // board empty
    private boolean isBoardFull() {
        for (String cell : board) {
            if (cell == null) return false;
        }
        return true;
    }

    @FXML

    // new game
    private void resetGame() {
        currentPlayer = "X";
        gameActive = true;
        board = new String[9];
        winnerLabel.setText("Winner:");

        for (javafx.scene.Node node : gameBoard.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setText("");
            }
        }
    }

    @FXML
    // new game and score
    private void resetScoreAndGame() {
        winCounterPlayer = 0;
        winCounterComputer = 0;
        playerWinsLabel.setText("0");
        computerWinsLabel.setText("0");
        resetGame();
    }
}

