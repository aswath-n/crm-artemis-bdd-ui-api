
package com.maersk.crm.beans.searchProviderResponse;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "unsorted",
    "sorted",
    "empty"
})
public class Sort {

    @JsonProperty("unsorted")
    private Boolean unsorted;
    @JsonProperty("sorted")
    private Boolean sorted;
    @JsonProperty("empty")
    private Boolean empty;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("unsorted")
    public Boolean getUnsorted() {
        return unsorted;
    }

    @JsonProperty("unsorted")
    public void setUnsorted(Boolean unsorted) {
        this.unsorted = unsorted;
    }

    @JsonProperty("sorted")
    public Boolean getSorted() {
        return sorted;
    }

    @JsonProperty("sorted")
    public void setSorted(Boolean sorted) {
        this.sorted = sorted;
    }

    @JsonProperty("empty")
    public Boolean getEmpty() {
        return empty;
    }

    @JsonProperty("empty")
    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
