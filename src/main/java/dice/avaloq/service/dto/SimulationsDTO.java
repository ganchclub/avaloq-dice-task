package dice.avaloq.service.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimulationsDTO {
    private long totalSimulations;
    private Map<String, Long> combinations = new LinkedHashMap<>();

    public long getTotalSimulations() {
        return totalSimulations;
    }

    public void setTotalSimulations(long totalSimulations) {
        this.totalSimulations = totalSimulations;
    }

    public Map<String, Long> getCombinations() {
        return combinations;
    }

    public void setCombinations(Map<String, Long> combinations) {
        this.combinations = combinations;
    }
}
