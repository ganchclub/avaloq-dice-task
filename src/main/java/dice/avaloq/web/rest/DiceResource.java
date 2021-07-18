package dice.avaloq.web.rest;

import dice.avaloq.service.DiceService;
import dice.avaloq.service.dto.SimulationDistributionDTO;
import dice.avaloq.service.dto.SimulationResultDTO;
import dice.avaloq.service.dto.SimulationsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * REST controller for dice distribution simulation.
 */
@RestController
@RequestMapping("/api/dice")
@Validated
@Slf4j
public class DiceResource {
    private final DiceService diceService;

    public DiceResource(DiceService diceService) {
        this.diceService = diceService;
    }

    /**
     * When creating a new resource, http method should be POST to be fully RESTful
     */
    @GetMapping("/simulation")
    public ResponseEntity<SimulationResultDTO> createDiceSimulation(
        @RequestParam("diceNumber") @NotNull @Min(1) Integer diceNumber,
        @RequestParam("diceSides") @NotNull @Min(4) Integer diceSides,
        @RequestParam("rollsNumber") @NotNull @Min(1) Integer rollsNumber
    ) {
        log.info("Simulating {} dice roll with {} sides {} times", diceNumber, diceSides, rollsNumber);
        return new ResponseEntity<>(diceService.simulate(diceNumber, diceSides, rollsNumber), HttpStatus.CREATED);
    }

    @GetMapping("/simulations")
    public ResponseEntity<SimulationsDTO> getAllSimulations() {
        log.info("Get all simulations requested");
        return new ResponseEntity<>(diceService.getAllSimulations(), HttpStatus.OK);
    }

    @GetMapping("/distribution/{diceNumber}x{diceSides}")
    public ResponseEntity<SimulationDistributionDTO> getSimulationDistribution(
        @PathVariable @Min(1) int diceNumber,
        @PathVariable @Min(4) int diceSides
    ) {
        log.info("Calculating {}x{} dice distribution across all simulations.", diceNumber, diceSides);
        return new ResponseEntity<>(diceService.getSimulationDistribution(diceNumber, diceSides), HttpStatus.OK);
    }
}
