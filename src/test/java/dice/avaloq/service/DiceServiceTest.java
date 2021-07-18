package dice.avaloq.service;

import dice.avaloq.IntegrationTest;
import dice.avaloq.repository.DiceSimulationRepository;
import dice.avaloq.service.dto.SimulationDistributionDTO;
import dice.avaloq.service.dto.SimulationResultDTO;
import dice.avaloq.service.dto.SimulationsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
class DiceServiceTest {
    @Autowired
    DiceSimulationRepository diceSimulationRepository;
    @Autowired
    DiceService diceService;

    @BeforeEach
    void init() {
        diceSimulationRepository.deleteAll();
    }

    @Test
    void simulate() {
        SimulationResultDTO simulationResultDTO = diceService.simulate(3, 6, 10000);
        assertEquals(16, simulationResultDTO.getTimesPerTotal().size());
    }

    @Test
    void getAllSimulations() {
        assertEquals(0, diceSimulationRepository.count());
        diceService.simulate(3, 6, 10000);
        diceService.simulate(1, 4, 100);
        diceService.simulate(1, 4, 100);
        diceService.simulate(1, 4, 100);

        SimulationsDTO allSimulations = diceService.getAllSimulations();
        assertEquals(4, diceSimulationRepository.count());
        assertEquals(4, allSimulations.getTotalSimulations());
        assertEquals(2, allSimulations.getCombinations().size());
    }

    @Test
    void getSimulationDistribution() {
        diceService.simulate(3, 6, 10000);
        diceService.simulate(3, 6, 50000);

        SimulationDistributionDTO distribution = diceService.getSimulationDistribution(3, 6);
        assertEquals(16, distribution.getDistributionMap().size());
    }
}