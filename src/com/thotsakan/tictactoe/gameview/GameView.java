package com.thotsakan.tictactoe.gameview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.thotsakan.tictactoe.R;

public abstract class GameView extends View {

	public GameView(Context context) {
		super(context);
	}

	public void displayResult(String result) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
		dialogBuilder.setTitle(R.string.dialog_results_title);
		dialogBuilder.setMessage(result);
		dialogBuilder.setPositiveButton(R.string.dialog_positive_newGame, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				newGame();
			}
		});
		dialogBuilder.setNegativeButton(R.string.dialog_negative_cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialogBuilder.show();
	}

	public abstract void newGame();

	public abstract void setFirstPlayerName(String name);

	public abstract void setSecondPlayerName(String name);
}
