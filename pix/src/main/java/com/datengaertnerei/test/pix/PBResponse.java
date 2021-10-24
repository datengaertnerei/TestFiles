
package com.datengaertnerei.test.pix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "total", "totalHits", "hits" })
@Generated("jsonschema2pojo")
public class PBResponse {

	public PBResponse() {
	}

	@JsonProperty("total")
	private Integer total;
	@JsonProperty("totalHits")
	private Integer totalHits;
	@JsonProperty("hits")
	private List<Hit> hits = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("total")
	public Integer getTotal() {
		return total;
	}

	@JsonProperty("total")
	public void setTotal(Integer total) {
		this.total = total;
	}

	@JsonProperty("totalHits")
	public Integer getTotalHits() {
		return totalHits;
	}

	@JsonProperty("totalHits")
	public void setTotalHits(Integer totalHits) {
		this.totalHits = totalHits;
	}

	@JsonProperty("hits")
	public List<Hit> getHits() {
		return hits;
	}

	@JsonProperty("hits")
	public void setHits(List<Hit> hits) {
		this.hits = hits;
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
