
package com.maersk.crm.beans.createProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uiid",
    "correlationId",
    "providers"
})
public class ProviderRequest {

    @JsonProperty("uiid")
    private String uiid;
    @JsonProperty("correlationId")
    private String correlationId;
    @JsonProperty("providers")
    private List<Provider> providers = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("uiid")
    public String getUiid() {
        return uiid;
    }

    @JsonProperty("uiid")
    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    @JsonProperty("correlationId")
    public String getCorrelationId() {
        return correlationId;
    }

    @JsonProperty("correlationId")
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @JsonProperty("providers")
    public List<Provider> getProviders() {
        return providers;
    }

    @JsonProperty("providers")
    public void setProviders(List<Provider> providers) {
        this.providers = providers;
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
