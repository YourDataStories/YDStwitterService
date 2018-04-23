package TweetService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractsCounter {

    private String label;
    private Integer value;

    public ContractsCounter() {
    }

    public String getLabel() {
        return this.label;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "data{" +
                "label=" + label +
                ", value='" + value + '\'' +
                '}';
    }
}
