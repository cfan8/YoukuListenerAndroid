package com.linangran.youkulistener.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.linangran.youkulistener.R;

/**
 * Created by linangran on 24/4/15.
 */
public class AccountActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
	GoogleApiClient apiClient;
	private boolean googleDialogShowing;
	private boolean signInClicked = false;
	private SignInButton signInButton;
	private Button signOutButton;
	private static final int RC_SIGN_IN = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		apiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN).build();
		signInButton = (SignInButton) findViewById(R.id.sign_in_button);
		signInButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (view.getId() == R.id.sign_in_button && !apiClient.isConnecting())
				{
					signInClicked = true;
					apiClient.connect();
				}
			}
		});
		this.setTitle(R.string.action_login);
		this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		signOutButton = (Button) findViewById(R.id.activity_account_logout);
		signOutButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				{
					if (apiClient.isConnected())
					{
						Plus.AccountApi.clearDefaultAccount(apiClient);
						apiClient.disconnect();
					}
				}
			}

		});
	}


	@Override
	protected void onStart()
	{
		super.onStart();
		//apiClient.connect();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		if (apiClient.isConnected())
		{
			apiClient.disconnect();
		}
	}

	@Override
	public void onConnected(Bundle bundle)
	{
		signInClicked = false;
		Toast.makeText(this, "Google Play Connected!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnectionSuspended(int i)
	{
		Toast.makeText(this, "Google Play Suspended!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult)
	{
		Toast.makeText(this, "Google Play Failed!", Toast.LENGTH_SHORT).show();
		if (!googleDialogShowing)
		{
			if (signInClicked && connectionResult.hasResolution())
			{
				try
				{
					googleDialogShowing = true;
					startIntentSenderForResult(connectionResult.getResolution().getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
				}
				catch (IntentSender.SendIntentException e)
				{
					// The intent was canceled before it was sent.  Return to the default
					// state and attempt to connect to get an updated ConnectionResult.
					googleDialogShowing = false;
					apiClient.connect();
				}
			}
		}

	}


	protected void onActivityResult(int requestCode, int responseCode, Intent intent)
	{
		if (requestCode == RC_SIGN_IN)
		{
			if (responseCode != RESULT_OK)
			{
				signInClicked = false;
			}
			googleDialogShowing = false;
			if (!apiClient.isConnected())
			{
				apiClient.reconnect();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (super.onOptionsItemSelected(item))
		{
			return true;
		}
		else
		{
			switch (item.getItemId())
			{
				case R.id.menu_account_refresh:
					return true;
				case R.id.menu_account_revoke:
					AlertDialog alertDialog = (new AlertDialog.Builder(this)).setTitle(R.string.confirmRevoke).setMessage(R.string.confirmRevokeWarning).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							if (apiClient.isConnected())
							{
								Plus.AccountApi.clearDefaultAccount(apiClient);
								Plus.AccountApi.revokeAccessAndDisconnect(apiClient).setResultCallback(new ResultCallback<Status>()
								{

									@Override
									public void onResult(Status status)
									{
										Toast.makeText(AccountActivity.this, R.string.accountRevoked, Toast.LENGTH_LONG).show();
										signInClicked = false;
									}
								});
							}
						}
					}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							return;
						}
					}).create();
					alertDialog.show();
					return true;
				default:
					return false;
			}
		}
	}
}
