package TweetService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contract {


    private Data data;
    
    public Contract() {
    }
    
  
    
   
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    
    @Override
    public String toString() {
        return  "data{" +
                "label='" + data.getLabel() + '\'' +
                ", value=" + data.getValue() +
                '}';
    }
}
