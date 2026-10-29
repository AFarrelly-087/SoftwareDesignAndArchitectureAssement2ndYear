package uk.ac.mmu.Game.applicationCode.domainmodel;

import java.util.Random;

public class RandomSingleDiceShaker implements DiceShaker{
    private static final Random random = new Random();

    @Override
    public boolean hasNext(){
        return true;
    }

    @Override
    public int next(){
        return random.nextInt(6) + 1;
    }
}
