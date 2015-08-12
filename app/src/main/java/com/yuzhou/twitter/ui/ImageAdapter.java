package com.yuzhou.twitter.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yuzhou.twitter.R;
import com.yuzhou.twitter.models.Media;

import java.util.List;

/**
 * Created by yuzhou on 2015/08/12.
 */
public class ImageAdapter extends ArrayAdapter<Media>
{
    private List<Media> mediaList;
    private int mediaCount;

    public ImageAdapter(Context context, List<Media> mediaList)
    {
        super(context, R.layout.item_image, mediaList);
        this.mediaList = mediaList;
        this.mediaCount = mediaList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image_iv_image);
            convertView.setTag(holder);
        }

        final Media image = getItem(position);
        Picasso.with(getContext()).load(image.getMediaUrl()).into(holder.image);

        return convertView;
    }

    private static class ViewHolder
    {
        private ImageView image;
    }

}
