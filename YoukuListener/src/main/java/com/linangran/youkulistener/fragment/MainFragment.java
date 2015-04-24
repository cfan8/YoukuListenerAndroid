package com.linangran.youkulistener.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import com.linangran.youkulistener.R;
import com.linangran.youkulistener.activity.AccountActivity;
import com.linangran.youkulistener.activity.SettingsActivity;

/**
 * Created by linangran on 24/4/15.
 */
public class MainFragment extends BaseFragment
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_main, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_settings:
				Intent settingsIntent = new Intent(this.getActivity(), SettingsActivity.class);
				startActivity(settingsIntent);
				break;
			case R.id.action_login:
				Intent loginIntent = new Intent(this.getActivity(), AccountActivity.class);
				startActivity(loginIntent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
