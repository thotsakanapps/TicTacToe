package com.thotsakan.tictactoe.ai;

import com.thotsakan.tictactoe.board.Board;

abstract class AbstractAIPlayer implements AIPlayer {
	protected Board board;

	public AbstractAIPlayer(Board board) {
		this.board = board;
	}
}
