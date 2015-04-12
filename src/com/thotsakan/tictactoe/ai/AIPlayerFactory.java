package com.thotsakan.tictactoe.ai;

import com.thotsakan.tictactoe.board.Board;

public final class AIPlayerFactory {

	public static AIPlayer getAIPlayer(int expertise, Board board) {
		switch (expertise) {
		case Expertise.EXPERT:
			return new ExpertAIPlayer(board);
		case Expertise.ADVANCED:
			return new AdvancedAIPlayer(board);
		case Expertise.NOVICE:
		default:
			return new NoviceAIPlayer(board);
		}
	}
}
