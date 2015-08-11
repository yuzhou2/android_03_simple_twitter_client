package com.yuzhou.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuzhou on 2015/08/11.
 */
@EqualsAndHashCode
@ToString
public class User
{
    @Getter @Setter private String name;
    @Getter @Setter private String profileImageUrl;
    @Getter @Setter private String screenName;

    public User()
    {
    }

    public User(JSONObject json)
    {
        try {
            name = json.getString("name");
            profileImageUrl = json.getString("profile_image_url");
            screenName = json.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getQScreenName()
    {
        if (screenName != null && !screenName.isEmpty()) {
            return "@" + screenName;
        }
        return screenName;
    }

}
