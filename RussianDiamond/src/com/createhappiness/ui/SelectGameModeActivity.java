package com.createhappiness.ui;

import com.createhappiness.russiandiamond.DiamondGameActivity;
import com.createhappiness.russiandiamond.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author yuanzeli@kingsoft.com
 * 
 *         选择游戏模式页（初始创建玩家资料页）
 * 
 */

public class SelectGameModeActivity extends Activity implements OnClickListener {

	private Button mTeamWorkModeBtn;
	private Button mPKModeBtn;
	private Context mContext;

	@Overridedeng 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_select_gamemode);
		mTeamWorkModeBtn = (Button) findViewById(R.id.teamwork_mode_btn);
		mTeamWorkModeBtn.setOnClickListener(this);
		mPKModeBtn = (Button) findViewById(R.id.pk_mode_btn);
		mPKModeBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mTeamWorkModeBtn == v) {
			Intent intent = new Intent(mContext,
					DiamondGameActivity.class);
			mContext.startActivity(intent);
		} else if (mPKModeBtn == v) {

		}
	}
}
