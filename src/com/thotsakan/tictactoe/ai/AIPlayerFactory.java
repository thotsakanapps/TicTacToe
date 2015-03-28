package com.thotsakan.tictactoe.ai;

import com.thotsakan.tictactoe.board.Board;

public final class AIPlayerFactory {

	public enum Expertise {
		ADVANCED, EXPERT, NOVICE;
	}

	public static AIPlayer getAIPlayer(Expertise expertise, Board board) {
		switch (expertise) {
		case EXPERT:
			return new ExpertAIPlayer(board);
		case ADVANCED:
			return new AdvancedAIPlayer(board);
		case NOVICE:
		default:
			return new NoviceAIPlayer(board);
		}
	}
}
