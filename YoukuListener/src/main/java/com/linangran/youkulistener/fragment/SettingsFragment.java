package com.linangran.youkulistener.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.linangran.youkulistener.R;
import com.linangran.youkulistener.util.PreferenceUtils;

/**
 * Created by linangran on 24/4/15.
 */
public class SettingsFragment extends PreferenceFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		this.getActivity().setTitle(R.string.action_settings);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		Preference aboutPreference = findPreference(PreferenceUtils.KEY_ABOUT_VERSION);
		try
		{
			aboutPreference.setSummary(aboutPreference.getSummary() + getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName);
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return view;
	}
}
