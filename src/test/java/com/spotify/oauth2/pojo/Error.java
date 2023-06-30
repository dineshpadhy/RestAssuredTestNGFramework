package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
//@Data
@Getter @Setter

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    @JsonProperty("status")
    private Integer status;
    @JsonProperty("message")
    private String message;

}
