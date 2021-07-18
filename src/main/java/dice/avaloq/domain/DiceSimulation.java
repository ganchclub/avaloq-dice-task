package dice.avaloq.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DiceSimulation extends AbstractBaseEntity {

    private int diceNumber;
    private int diceSides;
    @OneToMany(mappedBy = "diceSimulation", cascade = CascadeType.ALL)
    private List<Roll> rolls = new ArrayList<>();

    public DiceSimulation() {
    }

    public DiceSimulation(int diceNumber, int diceSides) {
        this.diceNumber = diceNumber;
        this.diceSides = diceSides;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    public int getDiceSides() {
        return diceSides;
    }

    public void setDiceSides(int diceSides) {
        this.diceSides = diceSides;
    }

    public void addRoll(Roll roll) {
        roll.setDiceSimulation(this);
        this.rolls.add(roll);
    }

    public void removeRoll(Roll roll) {
        roll.setDiceSimulation(null);
        this.rolls.remove(roll);
    }

    public List<Roll> getRolls() {
        return rolls;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DiceSimulation)) {
            return false;
        }
        return getId() != null && getId().equals(((DiceSimulation) object).getId());
    }
}
