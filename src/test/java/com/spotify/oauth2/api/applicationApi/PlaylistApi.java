package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {
    @Step
    public static Response post(Playlist requestPlaylist){
        return RestResource.post(USERS + "/"+ ConfigLoader.getInstance().getUserID()
                + PLAYLISTS, getToken(), requestPlaylist);
    }

    public static Response post(String token, Playlist requestPlaylist){
        return RestResource.post(USERS + "/"+ ConfigLoader.getInstance().getUserID()
                + PLAYLISTS, token, requestPlaylist);
    }
    public static Response get(String playlistId){
        return RestResource.get(PLAYLISTS + "/" +playlistId,getToken());
    }

    public static Response put(String playlistId, Playlist requestPlaylist){
        return RestResource.put(PLAYLISTS + "/"+playlistId,getToken(),requestPlaylist);
    }
}
