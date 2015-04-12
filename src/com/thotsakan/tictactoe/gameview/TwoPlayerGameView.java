package com.thotsakan.tictactoe.gameview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;

import com.thotsakan.tictactoe.board.Board;
import com.thotsakan.tictactoe.board.TileType;

final class TwoPlayerGameView extends GameView {

	private enum GameState {
		DRAW, FIRST_PLAYER_WON, GAME_OVER, ON_GOING, SECOND_PLAYER_WON;
	}

	private GameState currentState;

	private String firstPlayerName;

	private boolean manualPlayer;

	private String secondPlayerName;

	public TwoPlayerGameView(Context context) {
		super(context);
		board = new Board();
		newGame();
	}

	private void displayResult(GameState gameState) {
		String result;
		switch (gameState) {
		case SECOND_PLAYER_WON:
			result = secondPlayerName + " Won!";
			break;
		case FIRST_PLAYER_WON:
			result = firstPlayerName + " Won!";
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
			currentState = manualPlayer ? GameState.FIRST_PLAYER_WON : GameState.SECOND_PLAYER_WON;
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
		manualPlayer = true;
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
			makeMove(row, col);
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void setFirstPlayerName(String name) {
		firstPlayerName = name;
	}

	@Override
	public void setSecondPlayerName(String name) {
		secondPlayerName = name;
	}
}
