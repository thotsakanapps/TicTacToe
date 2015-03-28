package com.thotsakan.tictactoe.ai;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.thotsakan.tictactoe.board.Board;
import com.thotsakan.tictactoe.board.TileType;

final class AdvancedAIPlayer extends AbstractAIPlayer {

	public AdvancedAIPlayer(Board board) {
		super(board);
	}

	@Override
	public int[] getCompMove() {
		List<int[]> moves = board.getMoves();

		// Rule: If computer wins, make that move
		for (int[] move : moves) {
			board.setTileType(move[0], move[1], TileType.CROSS);
			boolean hasWon = board.hasWon();
			board.setTileType(move[0], move[1], TileType.BLANK);
			if (hasWon) {
				return move;
			}
		}

		// Rule: If manual wins, block that move
		for (int[] move : moves) {
			board.setTileType(move[0], move[1], TileType.NAUGHT);
			boolean hasWon = board.hasWon();
			board.setTileType(move[0], move[1], TileType.BLANK);
			if (hasWon) {
				return move;
			}
		}

		// Rule: Check if computer can fork
		for (int[] move : moves) {
			board.setTileType(move[0], move[1], TileType.CROSS);
			boolean chooseThisMove = false;

			List<int[]> manualMoves = board.getMoves();
			MANUAL_MOVE: for (int[] manualMove : manualMoves) {
				board.setTileType(manualMove[0], manualMove[1], TileType.CROSS);
				boolean compWon = board.hasWon();
				if (compWon) { // block the move where computer wins
					board.setTileType(manualMove[0], manualMove[1], TileType.NAUGHT);

					List<int[]> compMoves = board.getMoves();
					COMPUTER_MOVE: for (int[] compMove : compMoves) {
						board.setTileType(compMove[0], compMove[1], TileType.CROSS);
						chooseThisMove = board.hasWon();
						board.setTileType(compMove[0], compMove[1], TileType.BLANK);

						if (chooseThisMove) { // forked!
							break COMPUTER_MOVE;
						}
					}

					board.setTileType(manualMove[0], manualMove[1], TileType.BLANK);
				} else {
					board.setTileType(manualMove[0], manualMove[1], TileType.BLANK);
				}

				if (chooseThisMove) {
					break MANUAL_MOVE;
				}
			}

			board.setTileType(move[0], move[1], TileType.BLANK);
			if (chooseThisMove) {
				return move;
			}
		}

		Collections.shuffle(moves, new Random(System.currentTimeMillis()));
		return moves.get(0); // make any move
	}
}
