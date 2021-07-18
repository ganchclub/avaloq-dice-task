package dice.avaloq.service;

import dice.avaloq.domain.DiceSimulation;
import dice.avaloq.domain.Roll;
import dice.avaloq.repository.DiceSimulationRepository;
import dice.avaloq.service.dto.SimulationDistributionDTO;
import dice.avaloq.service.dto.SimulationResultDTO;
import dice.avaloq.service.dto.SimulationsDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DiceService {
    private final DiceSimulationRepository diceSimulationRepository;

    public DiceService(DiceSimulationRepository diceSimulationRepository) {
        this.diceSimulationRepository = diceSimulationRepository;
    }

    public SimulationResultDTO simulate(int diceNumber, int diceSides, int rollsNumber) {
        DiceSimulation simulation = new DiceSimulation(diceNumber, diceSides);
        Map<Integer, Integer> timesPerTotal = new LinkedHashMap<>();
        Random random = new Random();
        for (int i = 1; i <= rollsNumber; i++) {
            int total = 0;
            int combinationSum = 0;
            for (int d = 0; d < diceNumber; d++) {
                int sum = random.nextInt(diceSides) + 1;
                total += sum;
                combinationSum += sum;
            }
            simulation.addRoll(new Roll(combinationSum));
            int currentValue = 0;
            if (timesPerTotal.containsKey(total)) {
                currentValue = timesPerTotal.get(total);
            }
            timesPerTotal.put(total, currentValue + 1);
        }
        diceSimulationRepository.save(simulation);

        // sort by key for better readability
        Map<Integer, Integer> sorted = timesPerTotal
                                           .entrySet()
                                           .stream()
                                           .sorted(Map.Entry.comparingByKey())
                                           .collect(Collectors.toMap(
                                               Map.Entry::getKey,
                                               Map.Entry::getValue,
                                               (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return new SimulationResultDTO(sorted);
    }

    public SimulationsDTO getAllSimulations() {
        Map<String, Long> combinationsMap = new LinkedHashMap<>();
        List<Object[]> totalRollsByCombination = diceSimulationRepository.countTotalRollsByCombination();
        for (Object[] counts : totalRollsByCombination) {
            Integer diceNumber = (Integer) counts[0];
            Integer diceSides = (Integer) counts[1];
            Long combinationTotal = (Long) counts[2];
            combinationsMap.put(diceNumber + "x" + diceSides, combinationTotal);
        }
        SimulationsDTO simulationsDTO = new SimulationsDTO();
        simulationsDTO.setTotalSimulations(diceSimulationRepository.count());
        simulationsDTO.setCombinations(combinationsMap);

        return simulationsDTO;
    }

    public SimulationDistributionDTO getSimulationDistribution(int diceNumber, int diceSides) {
        int totalRollsByCombination = diceSimulationRepository.countByDiceNumberAndDiceSides(diceNumber, diceSides);
        List<Object[]> combinationCount = diceSimulationRepository.findByDiceNumberAndDiceSides(diceNumber, diceSides);
        Map<Integer, String> distributionMap = new HashMap<>();
        for (Object[] counts : combinationCount) {
            Integer diceSum = (Integer) counts[0];
            Double distribution = (Long) counts[1] / (double) totalRollsByCombination * 100;
            distributionMap.put(diceSum, String.format("%.2f%%", distribution));
        }

        return new SimulationDistributionDTO(distributionMap);
    }
}
