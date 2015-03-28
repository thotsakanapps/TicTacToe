package com.thotsakan.tictactoe.ai;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.thotsakan.tictactoe.board.Board;
import com.thotsakan.tictactoe.board.TileType;

final class ExpertAIPlayer extends AbstractAIPlayer {

	public ExpertAIPlayer(Board board) {
		super(board);
	}

	private int evaluate() {
		int score = 0;
		score += evaluateLine(0, 0, 0, 1, 0, 2); // row 0
		score += evaluateLine(1, 0, 1, 1, 1, 2); // row 1
		score += evaluateLine(2, 0, 2, 1, 2, 2); // row 2
		score += evaluateLine(0, 0, 1, 0, 2, 0); // col 0
		score += evaluateLine(0, 1, 1, 1, 2, 1); // col 1
		score += evaluateLine(0, 2, 1, 2, 2, 2); // col 2
		score += evaluateLine(0, 0, 1, 1, 2, 2); // diagonal
		score += evaluateLine(0, 2, 1, 1, 2, 0); // alternate diagonal
		return score;
	}

	private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
		int score = 0;

		// First cell
		if (board.getTileType(row1, col1) == TileType.CROSS) { // computer
			score = 1;
		} else if (board.getTileType(row1, col1) == TileType.NAUGHT) { // manual
			score = -1;
		}

		// Second cell
		if (board.getTileType(row2, col2) == TileType.CROSS) { // computer
			if (score == 1) { // previous computer
				score = 10;
			} else if (score == -1) { // previous manual
				return 0;
			} else { // previous blank
				score = 1;
			}
		} else if (board.getTileType(row2, col2) == TileType.NAUGHT) { // manual
			if (score == -1) { // previous manual
				score = -10;
			} else if (score == 1) { // previous computer
				return 0;
			} else { // previous blank
				score = -1;
			}
		}

		// Third cell
		if (board.getTileType(row3, col3) == TileType.CROSS) { // computer
			if (score > 0) { // previous blank + computer
				score *= 10;
			} else if (score < 0) { // previous blank + manual
				return 0;
			} else { // all empty
				score = 1;
			}
		} else if (board.getTileType(row3, col3) == TileType.NAUGHT) { // manual
			if (score < 0) { // previous blank + manual
				score *= 10;
			} else if (score > 1) { // previous blank + computer
				return 0;
			} else { // all empty
				score = -1;
			}
		}

		return score;
	}

	@Override
	public int[] getCompMove() {
		int[] result = minMax(2, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return new int[] { result[1], result[2] };
	}

	private int[] minMax(int depth, boolean manualPlayer, int alpha, int beta) {
		int bestRow = -1;
		int bestCol = -1;

		List<int[]> moves = board.getMoves();
		if (depth == 0 || moves.isEmpty()) {
			return new int[] { evaluate(), bestRow, bestCol };
		} else {
			Collections.shuffle(moves, new Random(System.currentTimeMillis()));
			for (int[] move : moves) {
				if (manualPlayer) {
					board.setTileType(move[0], move[1], TileType.NAUGHT);
				} else {
					board.setTileType(move[0], move[1], TileType.CROSS);
				}
				int currentScore = minMax(depth - 1, !manualPlayer, alpha, beta)[0];
				if (manualPlayer) {
					if (currentScore < beta) {
						beta = currentScore;
						bestRow = move[0];
						bestCol = move[1];
					}
				} else {
					if (currentScore > alpha) {
						alpha = currentScore;
						bestRow = move[0];
						bestCol = move[1];
					}
				}
				board.setTileType(move[0], move[1], TileType.BLANK);
				if (alpha >= beta) {
					break;
				}
			}
			return new int[] { manualPlayer ? beta : alpha, bestRow, bestCol };
		}
	}
}
