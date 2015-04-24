package com.linangran.youkulistener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.linangran.youkulistener.fragment.BaseFragment;

/**
 * Created by linangran on 24/4/15.
 */
public class BaseActivity extends AppCompatActivity
{
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			this.finish();
			return true;
		}
		else
		{
			return super.onOptionsItemSelected(item);
		}
	}
}
