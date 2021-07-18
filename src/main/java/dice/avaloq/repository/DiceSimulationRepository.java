package dice.avaloq.repository;

import dice.avaloq.domain.DiceSimulation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DiceSimulationRepository extends CrudRepository<DiceSimulation, Long>, DiceSimulationRepositoryCustom {
    @Query("select count(ds.id) from DiceSimulation as ds inner join ds.rolls r where ds.diceNumber=?1 and ds.diceSides=?2")
    int countByDiceNumberAndDiceSides(int diceNumber, int diceSides);
}
