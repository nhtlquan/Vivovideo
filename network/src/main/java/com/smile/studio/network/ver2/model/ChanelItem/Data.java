
package com.smile.studio.network.ver2.model.ChanelItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smile.studio.network.ver2.model.ListChanel.ChanelItem;

import java.util.List;

public class Data {

    @SerializedName("live_channel")
    @Expose
    private LiveChannel liveChannel;
    @SerializedName("related_channels")
    @Expose
    private List<ChanelItem> relatedChannels = null;
    @SerializedName("live_program")
    @Expose
    private LiveProgram liveProgram;

    public LiveChannel getLiveChannel() {
        return liveChannel;
    }

    public void setLiveChannel(LiveChannel liveChannel) {
        this.liveChannel = liveChannel;
    }

    public List<ChanelItem> getRelatedChannels() {
        return relatedChannels;
    }

    public void setRelatedChannels(List<ChanelItem> relatedChannels) {
        this.relatedChannels = relatedChannels;
    }

    public LiveProgram getLiveProgram() {
        return liveProgram;
    }

    public void setLiveProgram(LiveProgram liveProgram) {
        this.liveProgram = liveProgram;
    }

}
