package com.thotsakan.tictactoe.board;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.LruCache;

final class Tile extends Point {

	private static final LruCache<Integer, Bitmap> bitmapCache = new LruCache<Integer, Bitmap>(3);

	private static final Paint paintTile = new Paint();

	private int tileType;

	public Tile(int row, int column) {
		super(row, column);
		tileType = TileType.BLANK;
	}

	public void draw(Canvas canvas, Resources resources, float width, float height) {
		Bitmap bitmap = bitmapCache.get(tileType);
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(resources, TileType.RESOURCE_ID_ARRAY[tileType]);
			bitmapCache.put(tileType, bitmap);
		}
		canvas.drawBitmap(bitmap, null, new RectF(x * width, y * height, (x * width) + width, (y * height) + height), paintTile);
	}

	public int getTileType() {
		return tileType;
	}

	public void setTileType(int tileType) {
		this.tileType = tileType;
	}
}
