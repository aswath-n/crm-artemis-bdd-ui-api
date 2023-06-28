package com.maersk.crm.beans;

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
        "sort",
        "pageSize",
        "pageNumber",
        "offset",
        "unpaged",
        "paged"
})
public class Pageable {

    @JsonProperty("sort")
    private Sort sort;
    @JsonProperty("pageSize")
    private Integer pageSize;
    @JsonProperty("pageNumber")
    private Integer pageNumber;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("unpaged")
    private Boolean unpaged;
    @JsonProperty("paged")
    private Boolean paged;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sort")
    public Sort getSort() {
        return sort;
    }

    @JsonProperty("sort")
    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @JsonProperty("pageSize")
    public Integer getPageSize() {
        return pageSize;
    }

    @JsonProperty("pageSize")
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @JsonProperty("pageNumber")
    public Integer getPageNumber() {
        return pageNumber;
    }

    @JsonProperty("pageNumber")
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @JsonProperty("offset")
    public Integer getOffset() {
        return offset;
    }

    @JsonProperty("offset")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @JsonProperty("unpaged")
    public Boolean getUnpaged() {
        return unpaged;
    }

    @JsonProperty("unpaged")
    public void setUnpaged(Boolean unpaged) {
        this.unpaged = unpaged;
    }

    @JsonProperty("paged")
    public Boolean getPaged() {
        return paged;
    }

    @JsonProperty("paged")
    public void setPaged(Boolean paged) {
        this.paged = paged;
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