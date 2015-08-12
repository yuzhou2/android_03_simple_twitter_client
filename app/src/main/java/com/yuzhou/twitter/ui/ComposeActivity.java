package com.yuzhou.twitter.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.yuzhou.twitter.R;
import com.yuzhou.twitter.RestApplication;
import com.yuzhou.twitter.api.RestClient;
import com.yuzhou.twitter.models.User;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        queryUserCredential();
        setupActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {
            compose();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Intent getSupportParentActivityIntent()
    {
        finish();
        return null;
    }

    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
    }

    private void queryUserCredential()
    {
        RestClient client = RestApplication.getRestClient();
        client.getUserCredential(new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json)
            {
                // Response is automatically parsed into a JSONArray
                Log.d("DEBUG", "credential: " + json.toString());
                User user = new User(json);
                updateUserProfile(user);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void updateUserProfile(User user)
    {
        Picasso.with(this).load(user.getProfileImageUrl()).into((ImageView) findViewById(R.id.compose__iv_profile_image));
        ((TextView) findViewById(R.id.compose__tv_user_name)).setText(user.getName());
        ((TextView) findViewById(R.id.compose__tv_screen_name)).setText(user.getQScreenName());
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void compose()
    {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.error_unavailable_network, Toast.LENGTH_LONG).show();
            return;
        }

        EditText etText = (EditText) findViewById(R.id.compose__et_text_body);
        String message = etText.getText().toString().trim();
        if (message.isEmpty()) {
            Toast.makeText(this, R.string.warn_compose_empty_text, Toast.LENGTH_LONG).show();
        }

        RestClient client = RestApplication.getRestClient();
        client.postTweet(message, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json)
            {
                Log.d("DEBUG", "post: " + json.toString());
                setResult(RESULT_OK, getIntent());
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("ERROR", throwable.getMessage());
                Toast.makeText(getApplicationContext(), R.string.error_cannot_tweet, Toast.LENGTH_LONG).show();
            }
        });
    }

}
