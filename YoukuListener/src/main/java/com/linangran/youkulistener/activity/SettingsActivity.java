package com.linangran.youkulistener.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.linangran.youkulistener.R;
import com.linangran.youkulistener.fragment.SettingsFragment;

/**
 * Created by linangran on 24/4/15.
 */
public class SettingsActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		if (savedInstanceState == null)
		{
			getFragmentManager().beginTransaction().add(R.id.activity_setting_container, new SettingsFragment()).commit();
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
