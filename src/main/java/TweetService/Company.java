package TweetService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

	@JsonProperty("data")
    private CompanyData data;
    
    public Company() {
    }
   
    public CompanyData getData() {
        return data;
    }

    public void setCompanyData(CompanyData data) {
        this.data = data;
    }

    
    @Override
    public String toString() {
        return  "data{" +
                "aspect='" + data.getAspect() + '\'' +
                ", value=" + data.getValue() +
                '}';
    }
}
