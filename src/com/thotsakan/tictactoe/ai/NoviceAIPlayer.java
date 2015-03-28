package com.thotsakan.tictactoe.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.thotsakan.tictactoe.board.Board;
import com.thotsakan.tictactoe.board.TileType;

final class NoviceAIPlayer extends AbstractAIPlayer {

	private static final List<int[]> PREFERRED_MOVES = new ArrayList<int[]>();
	static {
		PREFERRED_MOVES.add(new int[] { 1, 1 });

		List<int[]> temp = new ArrayList<int[]>();
		temp.add(new int[] { 0, 0 });
		temp.add(new int[] { 0, 2 });
		temp.add(new int[] { 2, 0 });
		temp.add(new int[] { 2, 2 });
		Collections.shuffle(temp, new Random(System.currentTimeMillis()));
		PREFERRED_MOVES.addAll(temp);

		temp = new ArrayList<int[]>();
		temp.add(new int[] { 0, 1 });
		temp.add(new int[] { 1, 0 });
		temp.add(new int[] { 1, 2 });
		temp.add(new int[] { 2, 1 });
		Collections.shuffle(temp, new Random(System.currentTimeMillis()));
		PREFERRED_MOVES.addAll(temp);
	}

	public NoviceAIPlayer(Board board) {
		super(board);
	}

	@Override
	public int[] getCompMove() {
		for (int[] move : PREFERRED_MOVES) {
			if (board.getTileType(move[0], move[1]) == TileType.BLANK) {
				return move;
			}
		}
		return null;
	}
}
