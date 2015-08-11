package com.yuzhou.twitter.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yuzhou.twitter.R;
import com.yuzhou.twitter.RestApplication;
import com.yuzhou.twitter.api.RestClient;
import com.yuzhou.twitter.models.Tweet;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends ActionBarActivity
{
    private TweetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        adapter = new TweetAdapter(this, new ArrayList<Tweet>());
        ListView lvTimeline = (ListView) findViewById(R.id.lvTimeline);
        lvTimeline.setAdapter(adapter);
        lvTimeline.setEmptyView(findViewById(R.id.tvEmptyTimeline));

        RestClient client = RestApplication.getRestClient();
        client.getHomeTimeline(1, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json)
            {
                // Response is automatically parsed into a JSONArray
                Log.d("DEBUG", "timeline: " + json.toString());
                List<Tweet> tweets = Tweet.fromJson(json);
                adapter.addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
