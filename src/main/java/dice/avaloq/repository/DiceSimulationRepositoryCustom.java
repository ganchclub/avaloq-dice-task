package dice.avaloq.repository;

import java.util.List;

public interface DiceSimulationRepositoryCustom {
    List<Object[]> findByDiceNumberAndDiceSides(int diceNumber, int diceSides);
    List<Object[]> countTotalRollsByCombination();
}
