package com.thotsakan.tictactoe.gameview;

import android.content.Context;

public final class GameViewFactory {

	public static GameView getGame(Context context, boolean isSinglePlayerMode, int expertise) {
		GameView game = null;
		if (isSinglePlayerMode) {
			game = new SinglePlayerGameView(context, expertise);
		} else {
			game = new TwoPlayerGameView(context);
		}
		return game;
	}
}
