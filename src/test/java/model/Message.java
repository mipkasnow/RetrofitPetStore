package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(
        @JsonProperty("code")
        Integer code,
        @JsonProperty("type")
        String type,
        @JsonProperty("message")
        String message
) {
}
