package com.smile.studio.customplayer.model;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;

import com.smile.studio.customplayer.activity.PlayerActivity;

import java.util.UUID;

/**
 * Created by admin on 03/11/2016.
 */

public class PlaylistSample extends Sample {

    public final UriSample[] children;

    public PlaylistSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                          String[] drmKeyRequestProperties, boolean preferExtensionDecoders,
                          UriSample... children) {
        super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
        this.children = children;
    }

    @Override
    public Intent buildIntent(Context context) {
        String[] uris = new String[children.length];
        String[] extensions = new String[children.length];
        for (int i = 0; i < children.length; i++) {
            uris[i] = children[i].uri;
            extensions[i] = children[i].extension;
        }
        return super.buildIntent(context)
                .putExtra(PlayerActivity.URI_LIST_EXTRA, uris)
                .putExtra(PlayerActivity.EXTENSION_LIST_EXTRA, extensions)
                .setAction(PlayerActivity.ACTION_VIEW_LIST);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedArray(this.children, flags);
    }

    protected PlaylistSample(Parcel in) {
        super(in);
        this.children = in.createTypedArray(UriSample.CREATOR);
    }

    public static final Creator<PlaylistSample> CREATOR = new Creator<PlaylistSample>() {
        @Override
        public PlaylistSample createFromParcel(Parcel source) {
            return new PlaylistSample(source);
        }

        @Override
        public PlaylistSample[] newArray(int size) {
            return new PlaylistSample[size];
        }
    };
}
