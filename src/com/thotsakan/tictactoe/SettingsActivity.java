package com.thotsakan.tictactoe;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.NavUtils;

public final class SettingsActivity extends PreferenceActivity {

	private class HeadersFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.settings);
			PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.settings, false);
			setPlayerPreferences();
		}

		@Override
		public void onPause() {
			super.onPause();
			getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		}

		@Override
		public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
			String key = preference.getKey();

			if (key.equals(getString(R.string.market_rate_app_key))) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.class.getPackage().getName()));
				startActivity(intent);
			} else if (key.equals(getString(R.string.market_other_apps_by_us_key))) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://play.google.com/store/search?q=pub:" + getString(R.string.market_other_apps_by_us_publisher)));
				startActivity(intent);
			}
			return super.onPreferenceTreeClick(preferenceScreen, preference);
		}

		@Override
		public void onResume() {
			super.onResume();
			getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			if (key.equals(getString(R.string.single_player_switch_key))) {
				setPlayerPreferences();
			}
		}

		private void setPlayerPreferences() {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			boolean isSinglePlayerMode = prefs.getBoolean(getString(R.string.single_player_switch_key), Boolean.getBoolean(getString(R.bool.single_player_switch_defaultValue)));

			EditTextPreference firstPlayer = (EditTextPreference) findPreference(getString(R.string.first_player_key));
			if (isSinglePlayerMode) {
				firstPlayer.setDefaultValue(getString(R.string.single_player_defaultValue));
				firstPlayer.setDialogMessage(R.string.single_player_dialogMessage);
				firstPlayer.setDialogTitle(R.string.single_player_dialogTitle);
				firstPlayer.setSummary(R.string.single_player_summary);
				firstPlayer.setText(prefs.getString(getString(R.string.first_player_key), getString(R.string.single_player_defaultValue)));
				firstPlayer.setTitle(R.string.single_player_title);
			} else {
				firstPlayer.setDefaultValue(getString(R.string.first_player_defaultValue));
				firstPlayer.setDialogMessage(R.string.first_player_dialogMessage);
				firstPlayer.setDialogTitle(R.string.first_player_dialogTitle);
				firstPlayer.setSummary(R.string.first_player_summary);
				firstPlayer.setText(prefs.getString(getString(R.string.first_player_key), getString(R.string.first_player_defaultValue)));
				firstPlayer.setTitle(R.string.first_player_title);
			}

			findPreference(getString(R.string.second_player_key)).setEnabled(!isSinglePlayerMode);
		}
	}

	@Override
	public void onBackPressed() {
		NavUtils.navigateUpFromSameTask(this);
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		Fragment fragment = new HeadersFragment();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(android.R.id.content, fragment);
		transaction.commit();
	}
}
