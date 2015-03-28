package com.thotsakan.tictactoe.board;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Canvas;

public final class Board {

	private static final int[] WINS = { 7, 56, 73, 84, 146, 273, 292, 448 };

	private Tile[][] tiles;

	public Board() {
		tiles = new Tile[3][3];
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				tiles[row][col] = new Tile(row, col);
			}
		}
	}

	public void draw(Canvas canvas, Resources resources, int width, int height) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				tiles[row][col].draw(canvas, resources, width, height);
			}
		}
	}

	public List<int[]> getMoves() {
		List<int[]> moves = new ArrayList<int[]>();
		if (hasWon()) {
			return moves;
		}
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (tiles[row][col].getTileType() == TileType.BLANK) {
					moves.add(new int[] { row, col });
				}
			}
		}
		return moves;
	}

	public TileType getTileType(int row, int col) {
		return tiles[row][col].getTileType();
	}

	public boolean hasWon() {
		for (TileType tileType : new TileType[] { TileType.NAUGHT, TileType.CROSS }) {
			int config = 0;
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					if (tiles[row][col].getTileType() == tileType) {
						config |= (1 << (row * 3 + col));
					}
				}
			}
			for (int win : WINS) {
				if ((config & win) == win) {
					return true;
				}
			}
		}
		return false;
	}

	public void initBoard() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				tiles[row][col].setTileType(TileType.BLANK);
			}
		}
	}

	public void setTileType(int row, int col, TileType tileType) {
		tiles[row][col].setTileType(tileType);
	}
}
