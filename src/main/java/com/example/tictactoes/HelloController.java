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

    private Model model;

    @FXML
    public void initialize() {
        model = new Model();
        updateUI();
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        if (!model.isGameActive()) return;


        Button clickedButton = (Button) event.getSource();
        // transform pos into 1D Array
        int index = GridPane.getRowIndex(clickedButton) * 3 + GridPane.getColumnIndex(clickedButton);

        // is empty, null?
        if (model.makeMove(index)) {
            clickedButton.setText(model.getCurrentPlayer()); // set text currentplayer

            if (model.checkWinner()) {
                winnerLabel.setText(model.getCurrentPlayer() + " Wins!");
                updateScores();
            } else if (model.isBoardFull()) {
                winnerLabel.setText("It's a Draw!");
            } else { // change player
                model.togglePlayer();
                if (model.getCurrentPlayer().equals("O")) {
                    handleComputerMove(); // computers turn
                }
            }
        }
    }


    @FXML
    private void handleComputerMove() {
        if (!model.isGameActive()) return; // is game active? if not return

        int move = findBestMove();
        if (move != -1) { // if not equal to -1
            model.makeMove(move);
            Button button = (Button) gameBoard.getChildren().get(move); // possible 0-8 moveset
            button.setText("O");

            if (model.checkWinner()) {
                winnerLabel.setText("Computer Wins!"); // win
                updateScores();                        // scoreupdate
            } else if (model.isBoardFull()) {          // nowinner & fullcheck = draw
                winnerLabel.setText("It's a Draw!");
            } else {
                model.togglePlayer();                  // else change player.
            }
        }
    }

    @FXML
    private void updateScores() {
        playerWinsLabel.setText(String.valueOf(model.getWinCounterPlayer()));
        computerWinsLabel.setText(String.valueOf(model.getWinCounterComputer()));
    }

    @FXML
    private void updateUI() {
        String[] board = model.getBoard();
        for (int i = 0; i < gameBoard.getChildren().size(); i++) {
            Button button = (Button) gameBoard.getChildren().get(i);
            button.setText(board[i] == null ? "" : board[i]);
        } // convert to String
        playerWinsLabel.setText(String.valueOf(model.getWinCounterPlayer()));
        computerWinsLabel.setText(String.valueOf(model.getWinCounterComputer()));
        winnerLabel.setText("Winner:"); // reset winner label
    }

    @FXML

    // new game
    private void resetGame() {
        model.resetGame();
        updateUI();
    }

    @FXML
    // new game and score
    private void resetScoreAndGame() {
        model.resetScores();
        resetGame();
    }


    private int findBestMove() {
        Integer move;

        // Check for a winning move
        move = findWinningOrBlockingMove("O");
        if (move != null) return move;

        // Check for a blocking move
        move = findWinningOrBlockingMove("X");
        if (move != null) return move;

        // Take the center if available
        if (model.getBoard()[4] == null) return 4;

        // Take a corner if available
        int[] corners = {0, 2, 6, 8};
        for (int corner : corners) {
            if (model.getBoard()[corner] == null) return corner;
        }

        // Take any side if available
        int[] sides = {1, 3, 5, 7};
        for (int side : sides) {
            if (model.getBoard()[side] == null) return side;
        }

        return -1; // No moves available
    }

    // finding winning or blocking move for the player
    private Integer findWinningOrBlockingMove(String player) {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] condition : winConditions) {
            int countPlayer = 0, emptyIndex = -1;

            for (int index : condition) {
                if (player.equals(model.getBoard()[index])) {
                    countPlayer++;
                } else if (model.getBoard()[index] == null) {
                    emptyIndex = index;
                }
            }

            if (countPlayer == 2 && emptyIndex != -1) {
                return emptyIndex; // Found a winning or blocking move
            }
        }
        return null;
    }
}
