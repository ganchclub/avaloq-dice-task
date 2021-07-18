package dice.avaloq.web.rest;

import dice.avaloq.IntegrationTest;
import dice.avaloq.domain.DiceSimulation;
import dice.avaloq.domain.Roll;
import dice.avaloq.repository.DiceSimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
class DiceResourceTest {
    @Autowired
    private MockMvc restDiceMockMvc;

    @Autowired
    private DiceSimulationRepository diceRepository;

    @BeforeEach
    void cleanup() {
        diceRepository.deleteAll();
    }

    @Test
    void createDiceSimulationWillReturnBadRequestWhenParametersInvalid() throws Exception {
        restDiceMockMvc.perform(get("/api/dice/simulation?diceNumber=&diceSides=2"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void createDiceSimulation() throws Exception {
        restDiceMockMvc.perform(get("/api/dice/simulation?diceNumber=3&diceSides=6&rollsNumber=1000"))
            .andExpect(status().isCreated());

        // Validate the Dice Simulation in the database
        assertPersistedSimulations(
            simulations -> {
                assertThat(simulations).hasSize(1);
                DiceSimulation testDiceSimulation = simulations.iterator().next();
                assertThat(testDiceSimulation.getDiceNumber()).isEqualTo(3);
                assertThat(testDiceSimulation.getDiceSides()).isEqualTo(6);
                assertThat(testDiceSimulation.getRolls().size()).isEqualTo(1000);
            }
        );
    }

    @Test
    @Transactional
    void getAllSimulations() throws Exception {
        // initialize the database with dice simulation
        DiceSimulation diceSimulation = new DiceSimulation(3, 6);
        for (int i = 1; i <= 100; i++) {
            diceSimulation.addRoll(new Roll(12));
        }
        diceRepository.save(diceSimulation);

        restDiceMockMvc.perform(get("/api/dice/simulations"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.totalSimulations").value(1))
            .andExpect(jsonPath("$.combinations['3x6']").value(100));
    }

    @Test
    @Transactional
    void getSimulationDistribution() throws Exception {
        // initialize the database with dice simulation
        DiceSimulation diceSimulation = new DiceSimulation(3, 6);
        for (int i = 1; i <= 1000; i++) {
            // for test purposes use only 2 possible sums
            diceSimulation.addRoll(new Roll(new Random().nextInt(2) + 1));
        }
        diceRepository.save(diceSimulation);

        restDiceMockMvc.perform(get("/api/dice/distribution/3x6"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.distributionMap").value(aMapWithSize(2)));
    }

    private void assertPersistedSimulations(Consumer<Iterable<DiceSimulation>> diceAssertion) {
        diceAssertion.accept(diceRepository.findAll());
    }
}