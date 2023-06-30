package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;


import static com.spotify.oauth2.api.RestResource.postAccount;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public  class TokenManager {
    private static Instant expiry_time;
    private static String access_token;
    public synchronized static String getToken(){
        try{
            if(access_token == null ||  Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing Token ...");
                Response response = renewToken();
                access_token =response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            }
            else{
                System.out.println("Token is Good to Use!");
            }

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("ABORT!! Failed to get token");
        }
        return access_token;
    }

    private static Response renewToken(){
        HashMap<String, String> formParameter = new HashMap<>();
        formParameter.put("client_id", ConfigLoader.getInstance().getClientID());
        formParameter.put("client_secret",ConfigLoader.getInstance().getClientSecret());
        formParameter.put("grant_type",ConfigLoader.getInstance().getGrantType());
        formParameter.put("refresh_token",ConfigLoader.getInstance().getRefreshToken());

        Response response = postAccount(formParameter);

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT!! Renew Token failed!");
        }
        return response;

    }
}
