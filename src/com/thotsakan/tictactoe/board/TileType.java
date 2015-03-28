package com.thotsakan.tictactoe.board;

import com.thotsakan.tictactoe.R;

public enum TileType {
	BLANK(R.drawable.blank),

	CROSS(R.drawable.cross),

	NAUGHT(R.drawable.naught);

	private int drawableRep;

	private TileType(int drawableRep) {
		this.drawableRep = drawableRep;
	}

	int getDrawableRep() {
		return drawableRep;
	}
}
