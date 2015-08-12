package com.yuzhou.twitter.models;

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
public class Entity
{
    @Getter @Setter private List<Media> mediaList;

    public Entity()
    {
        mediaList = new ArrayList<>(0);
    }

    public Entity(JSONObject json)
    {
        mediaList = new ArrayList<>();
        try {
            mediaList.addAll(Media.fromJson(json.getJSONArray("media")));
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

}
