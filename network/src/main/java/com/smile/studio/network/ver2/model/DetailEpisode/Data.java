
package com.smile.studio.network.ver2.model.DetailEpisode;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("episode")
    @Expose
    private Episode episode;
    @SerializedName("related_episode")
    @Expose
    private List<com.smile.studio.network.ver2.model.SeasonOfSeries.Episode> relatedEpisode = null;

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public List<com.smile.studio.network.ver2.model.SeasonOfSeries.Episode> getRelatedEpisode() {
        return relatedEpisode;
    }

    public void setRelatedEpisode(List<com.smile.studio.network.ver2.model.SeasonOfSeries.Episode> relatedEpisode) {
        this.relatedEpisode = relatedEpisode;
    }

}
