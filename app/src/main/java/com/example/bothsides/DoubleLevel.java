/*
    Copyright 2020 Ryszard Jezierski

    This file is part of both][sides.

    both][sides is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    both][sides is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with both][sides.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.example.bothsides;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Double-track level, using two {@link GameManager} objects
 * @see GameManager
 * @author Ryszard Jezierski
 */
public class DoubleLevel extends AppCompatActivity implements Level{
	private GameManager gm1;
	private GameManager gm2;
	private double gm1_result;
	private double gm2_result;
	private boolean gm1_finished = false;
	private boolean gm2_finished = false;

	/**
	 * Gets game parameters from {@link MainActivity} via an Intent and initializes two new {@link GameManager} objects
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_double_level);

		Intent intent = getIntent();
		int tempo = intent.getIntExtra(MainActivity.EXTRA_TEMPO, 120);
		int metre = intent.getIntExtra(MainActivity.EXTRA_METER, 4);
		int measures = intent.getIntExtra(MainActivity.EXTRA_MEASURES, 4);
		boolean metronome = intent.getBooleanExtra(MainActivity.EXTRA_METRONOME_ENABLED, true);
		double[] rhythm1 = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM_1);
		double[] rhythm2 = intent.getDoubleArrayExtra(MainActivity.EXTRA_RHYTHM_2);
		double patternLength1 = intent.getDoubleExtra(MainActivity.EXTRA_PATTERN_LENGTH_1, 4.0);
		double patternLength2 = intent.getDoubleExtra(MainActivity.EXTRA_PATTERN_LENGTH_2, 4.0);

		RelativeLayout imgHolder1 = findViewById(R.id.img_view_double_1);
		RelativeLayout imgHolder2 = findViewById(R.id.img_view_double_2);

		gm1 = new GameManager(this, imgHolder1, tempo, metre, measures, rhythm1, patternLength1, metronome);
		gm2 = new GameManager(this, imgHolder2, tempo, metre, measures, rhythm2, patternLength2);
		gm1.start();
		gm2.start();
	}

	/**
	 * Button callback for the left track
	 * @param view this is an onClick function
	 */
	public void userInput1(View view) {
		gm1.processUserInput();
	}

	/**
	 * Button callback for the right track
	 * @param view this is an onClick function
	 */
	public void userInput2(View view) {
		gm2.processUserInput();
	}

	/**
	 * Callback for GameManager when the game ends
	 * @param gameManager GameManager instance calling this method
	 * @param result End score
	 */
	@Override
	public void endGame(GameManager gameManager, double result) {
		if (gameManager == gm1) {
			gm1_result = result;
			gm1_finished = true;
			Log.d("DoubleLevel", "gm1 finished");
		} else {
			gm2_result = result;
			gm2_finished = true;
			Log.d("DoubleLevel", "gm2 finished");

		}
		if (gm1_finished && gm2_finished) {
			switchToEndGameActivity();
		}

	}

	/**
	 * Calls {@link GameManager#cancelGame()} to cancel currently running game when activity is destroyed
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		gm1.cancelGame();
		gm2.cancelGame();
	}

	public void switchToEndGameActivity () {
		Intent intent = new Intent(this, EndGameActivity.class);
		intent.putExtra(MainActivity.EXTRA_RESULT_1, gm1_result);
		intent.putExtra(MainActivity.EXTRA_RESULT_2, gm2_result);
		intent.putExtra(MainActivity.EXTRA_HAS_RESULT_2, true);
		startActivity(intent);
	}
}