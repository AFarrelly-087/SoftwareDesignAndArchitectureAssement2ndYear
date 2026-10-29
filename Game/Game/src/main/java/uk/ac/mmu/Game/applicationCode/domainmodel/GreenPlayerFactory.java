package uk.ac.mmu.Game.applicationCode.domainmodel;

public class GreenPlayerFactory implements PlayerFactory {
    @Override
    public Player create(int start, int end, int[] positions) {
        return new Player(start, end, positions, 1, "Green");
    }
}
