package dice.avaloq.service.dto;

import java.util.Map;

public class SimulationDistributionDTO {
    private final Map<Integer, String> distributionMap;

    public SimulationDistributionDTO(Map<Integer, String> distributionMap) {
        this.distributionMap = distributionMap;
    }

    public Map<Integer, String> getDistributionMap() {
        return distributionMap;
    }
}
