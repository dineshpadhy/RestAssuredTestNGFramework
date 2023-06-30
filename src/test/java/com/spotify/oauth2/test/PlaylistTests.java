package com.spotify.oauth2.test;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.ErrorRoot;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.crypto.Data;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest{
    @Story("Create a playlist")
    @Description("This is the description for Should be able to create a playlist")
    @Test (description = "Should be able to create a playlist")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @Issue("123")
    @TmsLink("test-1")
    public void ShouldBeAbleToCreateAPlayist(){
        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertVoidEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    @TmsLink("test-2")
    public void ShouldBeAbleToGetAPlayist(){
        Playlist requestPlaylist = playlistBuilder("Updated Playlist Name","Updated playlist description.",false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistID());
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);
        assertVoidEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    @TmsLink("test-3")
    public void ShouldBeAbleToUpdateAPlayist(){
        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(), false);
        Response response = PlaylistApi.put(DataLoader.getInstance().updatePlaylistID(), requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);
    }

    @Test
    @TmsLink("test-4")
    @Story("Create a playlist")
    public void ShouldNotAbleToCreateAPlayistWithOutName(){
        Playlist requestPlaylist = playlistBuilder(null,generateDescription(),false );
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_400);
        assertError(response.as(ErrorRoot.class),StatusCode.CODE_400);
    }

    @Test
    @TmsLink("test-5")
    @Story("Create a playlist")
    public void ShouldNotAbleToCreateAPlayistWithExpireAccessToken(){
        String invalid_token = "12345";
        Playlist requestPlaylist = playlistBuilder(null,generateDescription(),false );
        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_401);
        assertError(response.as(ErrorRoot.class),StatusCode.CODE_401);
    }
    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public){
        return Playlist.builder().
                name(name).
                description(description).
                _public(_public).build();
    }
    @Step
    public void assertVoidEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }
    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode,equalTo(statusCode.code));
    }
    @Step
    public void assertError(ErrorRoot responseErr, StatusCode statusCode){
        assertThat(responseErr.getError().getStatus(),equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(),equalTo(statusCode.msg));

    }
}
