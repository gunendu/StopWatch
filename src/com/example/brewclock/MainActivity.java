package com.example.brewclock;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.os.CountDownTimer;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

	protected Button brewAddTime;
	protected Button brewDecreaseTime;
	protected Button startBrew;
	protected TextView brewCountLabel;
	protected TextView brewTimeLabel;

	protected int brewTime = 3;
	protected CountDownTimer brewCountDownTimer;
	protected int brewCount = 0;
	protected boolean isBrewing = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		brewAddTime = (Button) findViewById(R.id.brew_time_up);
		brewDecreaseTime = (Button) findViewById(R.id.brew_time_down);
		startBrew = (Button) findViewById(R.id.brew_start);
		brewCountLabel = (TextView) findViewById(R.id.brew_count_label);
		brewTimeLabel = (TextView) findViewById(R.id.brew_time);

		brewAddTime.setOnClickListener(this);
		brewDecreaseTime.setOnClickListener(this);
		startBrew.setOnClickListener(this);

		setBrewCount(0);
		setBrewTime(0);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == brewAddTime)
			setBrewTime(brewTime + 1);
		else if (v == brewDecreaseTime)
			setBrewTime(brewTime - 1);
		else if (v == startBrew)
			if (isBrewing)
				stopBrew();
			else
				startBrew();

	}

	public void setBrewTime(int minutes) {
		if (isBrewing)
			return;

		brewTime = minutes;

		if (brewTime < 1)
			brewTime = 1;

		brewTimeLabel.setText(String.valueOf(brewTime) + "m");
	}

	public void setBrewCount(int count) {
		brewCount = count;
		brewCountLabel.setText(String.valueOf(brewCount));
	}

	public void startBrew() {
		// Create a new CountDownTimer to track the brew time
		brewCountDownTimer = new CountDownTimer(brewTime * 60 * 1000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				brewTimeLabel.setText(String
						.valueOf(millisUntilFinished / 1000) + "s");
			}

			@Override
			public void onFinish() {
				isBrewing = false;
				setBrewCount(brewCount + 1);

				String url = "http://www.youtube.com/watch?v=eaOBeo-5PGo";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i); 
				startBrew.setText("Start");
			}
		};
		brewCountDownTimer.start();
		startBrew.setText("Stop");
		isBrewing = true;
	}

	public void stopBrew() {
		if (brewCountDownTimer != null)
			brewCountDownTimer.cancel();

		isBrewing = false;
		startBrew.setText("Start");
	}

}
