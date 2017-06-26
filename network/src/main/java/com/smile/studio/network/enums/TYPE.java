package com.smile.studio.network.enums;

/**
 * Created by admin on 16/08/2016.
 */
public enum TYPE {

    DEAULT(0), VIDEO(1), TVSHOW(2), SIGNLEMOVIE(1), MULTIEMOVIE(2), MOVIE(3), CHANNEL(4);

    int value;

    TYPE(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
