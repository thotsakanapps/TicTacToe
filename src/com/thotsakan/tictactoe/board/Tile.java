package com.thotsakan.tictactoe.board;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

final class Tile extends Point {

	private TileType tileType;

	public Tile(int row, int column) {
		super(row, column);
		tileType = TileType.BLANK;
	}

	public void draw(Canvas canvas, Resources resources, int width, int height) {
		Bitmap bitmap = BitmapFactory.decodeResource(resources, tileType.getDrawableRep());
		canvas.drawBitmap(bitmap, null, new Rect(x * width, y * height, (x * width) + width, (y * height) + height), new Paint());
	}

	public TileType getTileType() {
		return tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}
}
