package com.thotsakan.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.thotsakan.tictactoe.ai.Expertise;
import com.thotsakan.tictactoe.gameview.GameView;
import com.thotsakan.tictactoe.gameview.GameViewFactory;

public final class MainActivity extends Activity {

	private GameView gameView;

	private String getFirstPlayerName() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return preferences.getString(getString(R.string.first_player_key), getString(R.string.single_player_defaultValue));
	}

	private int getPlayerExpertise() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String expertise = preferences.getString(getString(R.string.player_expertise_key), null);
		if (getString(R.string.player_expertise_novice).equals(expertise)) {
			return Expertise.NOVICE;
		} else if (getString(R.string.player_expertise_advanced).equals(expertise)) {
			return Expertise.ADVANCED;
		} else if (getString(R.string.player_expertise_expert).equals(expertise)) {
			return Expertise.EXPERT;
		} else {
			return Expertise.ADVANCED;
		}
	}

	private String getSecondPlayerName() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return preferences.getString(getString(R.string.second_player_key), getString(R.string.second_player_defaultValue));
	}

	private boolean isSinglePlayerMode() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return preferences.getBoolean(getString(R.string.single_player_switch_key), Boolean.valueOf(getString(R.bool.single_player_switch_defaultValue)));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gameView = GameViewFactory.getGame(this, isSinglePlayerMode(), getPlayerExpertise());
		gameView.setFirstPlayerName(getFirstPlayerName());
		gameView.setSecondPlayerName(getSecondPlayerName());

		LinearLayout gameBoard = (LinearLayout) findViewById(R.id.game_view);
		gameBoard.addView(gameView);

		// AdMob
		AdView mAdView = (AdView) findViewById(R.id.ad_view);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_game:
			gameView.newGame();
			break;
		case R.id.action_settings:
			Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
