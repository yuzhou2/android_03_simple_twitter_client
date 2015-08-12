package com.yuzhou.twitter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuzhou on 2015/08/12.
 */
@EqualsAndHashCode
@ToString
public class Media
{
    @Getter @Setter private String mediaUrl;

    public Media()
    {
    }

    public Media(JSONObject json)
    {
        try {
            mediaUrl = json.getString("media_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final List<Media> fromJson(JSONArray jsonArray)
    {
        List<Media> mediaList = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject mediaJson = jsonArray.getJSONObject(i);
                Media media = new Media(mediaJson);
                mediaList.add(media);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        return mediaList;
    }

}
