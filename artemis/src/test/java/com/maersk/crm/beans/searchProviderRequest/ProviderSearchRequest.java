
package com.maersk.crm.beans.searchProviderRequest;

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
    "providerSearch",
    "from",
    "size"
})
public class ProviderSearchRequest {

    @JsonProperty("providerSearch")
    private ProviderSearch providerSearch;
    @JsonProperty("from")
    private Integer from;
    @JsonProperty("size")
    private Integer size;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("providerSearch")
    public ProviderSearch getProviderSearch() {
        return providerSearch;
    }

    @JsonProperty("providerSearch")
    public void setProviderSearch(ProviderSearch providerSearch) {
        this.providerSearch = providerSearch;
    }

    @JsonProperty("from")
    public Integer getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(Integer from) {
        this.from = from;
    }

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
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
