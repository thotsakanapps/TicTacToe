package com.thotsakan.tictactoe.gameview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;

import com.thotsakan.tictactoe.ai.AIPlayer;
import com.thotsakan.tictactoe.ai.AIPlayerFactory;
import com.thotsakan.tictactoe.board.Board;
import com.thotsakan.tictactoe.board.TileType;

final class SinglePlayerGameView extends GameView {

	private enum GameState {
		COMPUTER_WON, DRAW, GAME_OVER, MANUAL_WON, ON_GOING;
	}

	private AIPlayer computerPlay;

	private GameState currentState;

	private boolean manualPlayer;

	private String playerName;

	public SinglePlayerGameView(Context context, int expertise) {
		super(context);
		board = new Board();
		computerPlay = AIPlayerFactory.getAIPlayer(expertise, board);
		newGame();
	}

	private boolean computerMove() {
		int[] compMove = computerPlay.getCompMove();
		return makeMove(compMove[0], compMove[1]);
	}

	private void displayResult(GameState gameState) {
		String result;
		switch (gameState) {
		case COMPUTER_WON:
			result = playerName + " Lost!";
			break;
		case MANUAL_WON:
			result = playerName + " Won!";
			break;
		case GAME_OVER:
			result = "Game Over!";
			break;
		case DRAW:
			result = "Draw!";
			break;
		case ON_GOING:
		default:
			result = "Game is still on!";
		}
		displayResult(result);
	}

	private boolean makeMove(int row, int col) {
		if (currentState == GameState.GAME_OVER) {
			return false; // no move
		}
		if (board.getTileType(row, col) != TileType.BLANK) {
			return false; // no move
		}
		board.setTileType(row, col, manualPlayer ? TileType.NAUGHT : TileType.CROSS);
		invalidate();
		if (board.hasWon()) {
			currentState = manualPlayer ? GameState.MANUAL_WON : GameState.COMPUTER_WON;
			displayResult(currentState);
			currentState = GameState.GAME_OVER;
			return false; // no more moves
		} else if (board.getMoves().isEmpty()) {
			currentState = GameState.DRAW;
			displayResult(currentState);
			currentState = GameState.GAME_OVER;
			return false; // no more moves
		}
		manualPlayer = !manualPlayer;
		return true; // move made
	}

	@Override
	public void newGame() {
		board.initBoard();
		currentState = GameState.ON_GOING;
		manualPlayer = Math.random() < 0.5;
		if (!manualPlayer) {
			computerMove();
		}
		invalidate();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int boardSize = Math.min(getWidth(), getHeight());
		int tileSize = boardSize / 3;
		int row = (int) (event.getX() / tileSize);
		int col = (int) (event.getY() / tileSize);
		if (row < 3 && col < 3) {
			if (makeMove(row, col)) {
				computerMove();
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void setFirstPlayerName(String name) {
		playerName = name;
	}

	@Override
	public void setSecondPlayerName(String name) {
	}
}
