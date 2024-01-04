package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IdName(
        @JsonProperty("id")
        Long id,
        @JsonProperty("name")
        String name
) {
}
