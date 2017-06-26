package com.smile.studio.customplayer.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;

import com.smile.studio.customplayer.activity.PlayerActivity;
import com.smile.studio.customplayer.utils.DebugPlayer;

import java.util.UUID;

/**
 * Created by admin on 03/11/2016.
 */

public class UriSample extends Sample {

    public final String uri;
    public final String extension;

    public UriSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                     String[] drmKeyRequestProperties, boolean preferExtensionDecoders, String uri,
                     String extension) {
        super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
        this.uri = uri;
        this.extension = extension;
    }

    public void trace() {
        super.trace();
        DebugPlayer.e("Uri: " + uri +"\nextension: " + extension);
    }

    @Override
    public Intent buildIntent(Context context) {
        return super.buildIntent(context)
                .setData(Uri.parse(uri))
                .putExtra(PlayerActivity.EXTENSION_EXTRA, extension)
                .setAction(PlayerActivity.ACTION_VIEW);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.uri);
        dest.writeString(this.extension);
    }

    protected UriSample(Parcel in) {
        super(in);
        this.uri = in.readString();
        this.extension = in.readString();
    }

    public static final Creator<UriSample> CREATOR = new Creator<UriSample>() {
        @Override
        public UriSample createFromParcel(Parcel source) {
            return new UriSample(source);
        }

        @Override
        public UriSample[] newArray(int size) {
            return new UriSample[size];
        }
    };
}
