package com.thotsakan.tictactoe.gameview;

import android.content.Context;

import com.thotsakan.tictactoe.ai.AIPlayerFactory.Expertise;

public final class GameViewFactory {

	public static GameView getGame(Context context, boolean isSinglePlayerMode, Expertise expertise) {
		GameView game = null;
		if (isSinglePlayerMode) {
			game = new SinglePlayerGameView(context, expertise);
		} else {
			game = new TwoPlayerGameView(context);
		}
		return game;
	}
}
