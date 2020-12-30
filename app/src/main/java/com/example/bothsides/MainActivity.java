package com.example.bothsides;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
	//values common for both rhythm tracks
	public static final String EXTRA_TEMPO = "com.example.bothsides.TEMPO";
	public static final String EXTRA_METRE = "com.example.bothsides.METRE";
	public static final String EXTRA_MEASURES = "com.example.bothsides.MEASURES";
	//values for individual rhythm tracks
	public static final String EXTRA_RHYTHM_1 = "com.example.bothsides.RHYTHM_1";
	public static final String EXTRA_RHYTHM_2 = "com.example.bothsides.RHYTHM_2";
	public static final String EXTRA_PATTERN_LENGTH_1 = "com.example.bothsides.PATTERN_LENGTH_1";
	public static final String EXTRA_PATTERN_LENGTH_2 = "com.example.bothsides.PATTERN_LENGTH_2";
	public static final String EXTRA_RESULT_1 = "com.example.bothsides.RESULTS_1";
	public static final String EXTRA_RESULT_2 = "com.example.bothsides.RESULTS_2";
	public static final String EXTRA_HAS_RESULT_2 = "com.example.bothsides.HAS_RESULTS_2";

	private static class RhythmPattern {
		public String name;
		public int image;
		public double patternLength;
		public double[] pattern;

		RhythmPattern(String name, int image, double patternLength, double[] pattern) {
			this.name = name;
			this.image = image;
			this.patternLength = patternLength;
			this.pattern = pattern;
		}
	}
	private final ArrayList<RhythmPattern> rhythmPatternList = new ArrayList<>();
	private RhythmPattern chosenRhythmPattern1;
	private RhythmPattern chosenRhythmPattern2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeRhythmPatternList();
		ImageArrayAdapter adapter = initializeImageArrayAdapter();

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setDropDownWidth(metrics.widthPixels);
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				chosenRhythmPattern1 = rhythmPatternList.get(position);
				Log.d("Spinner1", chosenRhythmPattern1.name);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setDropDownWidth(metrics.widthPixels);
		spinner2.setAdapter(adapter);
		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				chosenRhythmPattern2 = rhythmPatternList.get(position);
				Log.d("Spinner2", chosenRhythmPattern2.name);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	public void startSingleLevel(View view) {
		Intent intent = new Intent(this, SingleLevel.class);
		intent.putExtra(EXTRA_TEMPO, 60.0);
		intent.putExtra(EXTRA_METRE, 4);
		intent.putExtra(EXTRA_MEASURES, 4);
		intent.putExtra(EXTRA_RHYTHM_1, chosenRhythmPattern1.pattern);
		intent.putExtra(EXTRA_PATTERN_LENGTH_1, chosenRhythmPattern1.patternLength);
		startActivity(intent);
	}

	public void startDoubleLevel(View view) {
		Intent intent = new Intent(this, DoubleLevel.class);
		intent.putExtra(EXTRA_TEMPO, 60.0);
		intent.putExtra(EXTRA_METRE, 4);
		intent.putExtra(EXTRA_MEASURES, 4);
		intent.putExtra(EXTRA_RHYTHM_1, chosenRhythmPattern1.pattern);
		intent.putExtra(EXTRA_RHYTHM_2, chosenRhythmPattern2.pattern);
		intent.putExtra(EXTRA_PATTERN_LENGTH_1, chosenRhythmPattern1.patternLength);
		intent.putExtra(EXTRA_PATTERN_LENGTH_2, chosenRhythmPattern2.patternLength);

		startActivity(intent);
	}

	private void initializeRhythmPatternList() {
		rhythmPatternList.add(new RhythmPattern("Eight notes", R.drawable.papryka, 0.5, new double[]{0.0}));
		rhythmPatternList.add(new RhythmPattern("Quarter notes", R.drawable.papryka, 1.0, new double[]{0.0}));
	}

	private ImageArrayAdapter initializeImageArrayAdapter() {
		String[] listItems = new String[rhythmPatternList.size()];
		int[] listImages = new int[rhythmPatternList.size()];
		for (int i = 0; i < rhythmPatternList.size(); i++) {
			listItems[i] = rhythmPatternList.get(i).name;
			listImages[i] = rhythmPatternList.get(i).image;
		}
		return new ImageArrayAdapter(this, listItems, listImages);
	}

}
