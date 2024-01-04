package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Pet(
        @JsonProperty("id")
        Long id,
        @JsonProperty("category")
        IdName category,
        @JsonProperty("name")
        String name,
        @JsonProperty("photoUrls")
        List<String> photoUrls,
        @JsonProperty("tags")
        List<IdName> tags,
        @JsonProperty("status")
        String status
) {
}
