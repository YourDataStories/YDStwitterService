package TweetService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyData {
	@JsonProperty("value")
    private String value;
	@JsonProperty("aspect")
    private String aspect;

    public CompanyData() {
    }

    public String getAspect() {
        return this.aspect;
    }

    public String getValue() {
        return this.value;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "data{" +
                "aspect=" + aspect +
                ", value='" + value + '\'' +
                '}';
    }
}
