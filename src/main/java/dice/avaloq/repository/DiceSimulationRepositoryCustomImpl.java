package dice.avaloq.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class DiceSimulationRepositoryCustomImpl implements DiceSimulationRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    public List<Object[]> findByDiceNumberAndDiceSides(int diceNumber, int diceSides) {
        String hql = "select r.total, count(ds.id) from DiceSimulation as ds " +
                         "inner join ds.rolls r " +
                         "where ds.diceNumber = :diceNumber " +
                         "and ds.diceSides = :diceSides " +
                         "group by r.total";

        Query query = em.createQuery(hql);

        query.setParameter("diceNumber", diceNumber);
        query.setParameter("diceSides", diceSides);

        @SuppressWarnings("unchecked")
        List<Object[]> counts = query.getResultList();

        return counts;
    }

    @Override
    public List<Object[]> countTotalRollsByCombination() {
        String hql = "select ds.diceNumber, ds.diceSides, count(*) from DiceSimulation as ds " +
                         "inner join ds.rolls r " +
                         "group by ds.diceNumber, ds.diceSides";

        @SuppressWarnings("unchecked")
        List<Object[]> counts = em.createQuery(hql).getResultList();

        return counts;
    }
}
