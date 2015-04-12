package com.thotsakan.tictactoe.gameview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.thotsakan.tictactoe.R;
import com.thotsakan.tictactoe.board.Board;

public abstract class GameView extends View {

	private static final Paint paint;

	static {
		paint = new Paint();
		paint.setARGB(255, 0, 0, 0);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(10);
	}

	protected Board board;

	public GameView(Context context) {
		super(context);
	}

	public void displayResult(String result) {
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		final String showResultsKey = getResources().getString(R.string.dialog_results_switch_key);

		if (prefs.getBoolean(showResultsKey, true)) {
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
			dialogBuilder.setTitle(R.string.dialog_results_title);
			dialogBuilder.setMessage(result);
			dialogBuilder.setPositiveButton(R.string.dialog_results_newGame, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					newGame();
				}
			});
			dialogBuilder.setNegativeButton(R.string.dialog_results_cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Editor editor = prefs.edit();
					editor.putBoolean(showResultsKey, false);
					editor.apply();
					dialog.dismiss();
				}
			});
			dialogBuilder.show();
		} else {
			Toast toast = Toast.makeText(getContext(), result, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, (int) (getHeight() * 0.3));
			toast.show();
		}
	}

	public abstract void newGame();

	@Override
	protected void onDraw(Canvas canvas) {
		float boardSize = Math.min(getWidth(), getHeight());
		float tileSize = boardSize / 3;
		board.draw(canvas, getResources(), tileSize, tileSize);
		for (int i = 1; i < 3; i++) {
			float tileEnd = i * tileSize;
			canvas.drawLine(tileEnd, 0, tileEnd, boardSize, paint); // vertical
			canvas.drawLine(0, tileEnd, boardSize, tileEnd, paint); // horizontal
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(widthMeasureSpec) * 0.95), MeasureSpec.AT_MOST);
		int measuredheight = MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(heightMeasureSpec) * 0.95), MeasureSpec.AT_MOST);
		setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), measuredWidth), getDefaultSize(getSuggestedMinimumHeight(), measuredheight));
	}

	public abstract void setFirstPlayerName(String name);

	public abstract void setSecondPlayerName(String name);
}
