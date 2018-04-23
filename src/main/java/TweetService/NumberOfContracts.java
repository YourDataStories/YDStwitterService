package TweetService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NumberOfContracts {


    private ContractsCounter data;
    
    public NumberOfContracts() {
    }
    
  
    
   
    public ContractsCounter getData() {
        return data;
    }

    public void setData(ContractsCounter data) {
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
