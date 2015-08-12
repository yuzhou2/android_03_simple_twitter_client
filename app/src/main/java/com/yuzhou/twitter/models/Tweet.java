package com.yuzhou.twitter.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuzhou on 2015/08/10.
 */
@EqualsAndHashCode
@ToString
public class Tweet
{
    @Getter @Setter private long createTime;
    @Getter @Setter private String text;
    @Getter @Setter private int retweetCount;
    @Getter @Setter private int favoriteCount;
    @Getter @Setter private User user = new User();
    @Getter @Setter private Entity entities = new Entity();
    @Getter @Setter private Entity extendedEntities = new Entity();

    // Make sure to always define this constructor with no arguments
    public Tweet()
    {
    }

    // Add a constructor that creates an object from the JSON response
    public Tweet(JSONObject json)
    {
        try {
            user = new User(json.getJSONObject("user"));
            createTime = getTwitterDate(json.getString("created_at")).getTime();
            text = json.getString("text");
            retweetCount = json.getInt("retweet_count");
            favoriteCount = json.getInt("favorite_count");
            entities = new Entity(json.getJSONObject("entities"));

            if (!json.isNull("extended_entities")) {
                extendedEntities = new Entity(json.getJSONObject("extended_entities"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Date getTwitterDate(String date) throws ParseException
    {
        final String twitterTimeFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterTimeFormat);
        sf.setLenient(true);
        return sf.parse(date);
    }

    public static final List<Tweet> fromJson(JSONArray jsonArray)
    {
        List<Tweet> tweets = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Log.d("DEBUG", "tweet: " + tweetJson.toString());
                Tweet tweet = new Tweet(tweetJson);
                tweets.add(tweet);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }

}