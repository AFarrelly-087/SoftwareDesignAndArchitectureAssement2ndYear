package uk.ac.mmu.Game.applicationCode.domainmodel;

import java.util.Iterator;
import java.util.List;

public class FixedDiceShaker implements DiceShaker{
    private final Iterator<Integer> rolls;

    public FixedDiceShaker(List<Integer> rolls) {
        this.rolls = rolls.iterator();
    }

    @Override
    public boolean hasNext() {
        return rolls.hasNext();
    }

    @Override
    public int next() {
        return rolls.next();
    }
}
