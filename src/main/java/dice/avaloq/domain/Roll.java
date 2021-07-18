package dice.avaloq.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Roll extends AbstractBaseEntity {
    private Integer total;
    @ManyToOne
    private DiceSimulation diceSimulation;

    public Roll() {
    }

    public Roll(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public DiceSimulation getDiceSimulation() {
        return diceSimulation;
    }

    public void setDiceSimulation(DiceSimulation diceSimulation) {
        this.diceSimulation = diceSimulation;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Roll)) {
            return false;
        }
        return getId() != null && getId().equals(((Roll) object).getId());
    }
}
