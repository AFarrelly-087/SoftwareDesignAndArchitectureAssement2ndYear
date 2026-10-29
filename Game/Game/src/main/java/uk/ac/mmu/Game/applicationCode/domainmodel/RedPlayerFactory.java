package uk.ac.mmu.Game.applicationCode.domainmodel;

public class RedPlayerFactory implements PlayerFactory {
    @Override
    public Player create(int start, int end, int[] positions) {
        return new Player(start, end, positions, -1, "Red");
    }
}
