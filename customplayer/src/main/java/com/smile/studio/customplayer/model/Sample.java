package com.smile.studio.customplayer.model;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.smile.studio.customplayer.activity.PlayerActivity;
import com.smile.studio.customplayer.utils.DebugPlayer;

import java.util.UUID;

/**
 * Created by admin on 03/11/2016.
 */

public class Sample implements Parcelable {

    public final String name;
    public final boolean preferExtensionDecoders;
    public final UUID drmSchemeUuid;
    public final String drmLicenseUrl;
    public final String[] drmKeyRequestProperties;

    public Sample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                  String[] drmKeyRequestProperties, boolean preferExtensionDecoders) {
        this.name = name;
        this.drmSchemeUuid = drmSchemeUuid;
        this.drmLicenseUrl = drmLicenseUrl;
        this.drmKeyRequestProperties = drmKeyRequestProperties;
        this.preferExtensionDecoders = preferExtensionDecoders;
    }

    public void trace() {
        DebugPlayer.e("name: " + name + "\nDRM " + drmSchemeUuid + "\nDRM License: " + drmLicenseUrl + "\nDRM PreferExtensionDecoders: " + preferExtensionDecoders);
        if (drmKeyRequestProperties != null)
            for (String string : drmKeyRequestProperties) {
                DebugPlayer.e(" " + string);
            }
    }

    public Intent buildIntent(Context context) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(PlayerActivity.PREFER_EXTENSION_DECODERS, preferExtensionDecoders);
        if (drmSchemeUuid != null) {
            intent.putExtra(PlayerActivity.DRM_SCHEME_UUID_EXTRA, drmSchemeUuid.toString());
            intent.putExtra(PlayerActivity.DRM_LICENSE_URL, drmLicenseUrl);
            intent.putExtra(PlayerActivity.DRM_KEY_REQUEST_PROPERTIES, drmKeyRequestProperties);
        }
        return intent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.preferExtensionDecoders ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.drmSchemeUuid);
        dest.writeString(this.drmLicenseUrl);
        dest.writeStringArray(this.drmKeyRequestProperties);
    }

    protected Sample(Parcel in) {
        this.name = in.readString();
        this.preferExtensionDecoders = in.readByte() != 0;
        this.drmSchemeUuid = (UUID) in.readSerializable();
        this.drmLicenseUrl = in.readString();
        this.drmKeyRequestProperties = in.createStringArray();
    }

    public static final Creator<Sample> CREATOR = new Creator<Sample>() {
        @Override
        public Sample createFromParcel(Parcel source) {
            return new Sample(source);
        }

        @Override
        public Sample[] newArray(int size) {
            return new Sample[size];
        }
    };
}
