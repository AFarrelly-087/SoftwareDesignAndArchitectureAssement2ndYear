package uk.ac.mmu.Game.applicationCode.domainmodel;

import java.util.Random;

public class RandomDoubleDiceShaker implements DiceShaker{
    private static final Random random = new Random();

    @Override
    public boolean hasNext(){
        return true;
    }

    @Override
    public int next(){
        return (random.nextInt(6)+1) + (random.nextInt(6) + 1);
    }
}
