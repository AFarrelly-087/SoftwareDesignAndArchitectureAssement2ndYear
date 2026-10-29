package uk.ac.mmu.Game.applicationCode.domainmodel;

public interface PlayerFactory {
    Player create(int start, int end, int[] positions);
}
