package dice.avaloq.service.dto;

import java.util.Map;

public class SimulationResultDTO {
    private final Map<Integer, Integer> timesPerTotal;

    public SimulationResultDTO(Map<Integer, Integer> timesPerTotal) {
        this.timesPerTotal = timesPerTotal;
    }

    public Map<Integer, Integer> getTimesPerTotal() {
        return timesPerTotal;
    }
}
