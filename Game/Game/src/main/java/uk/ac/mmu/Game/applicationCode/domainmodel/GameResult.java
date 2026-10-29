package uk.ac.mmu.Game.applicationCode.domainmodel;

import java.util.List;

public record GameResult(GameVariation variation, boolean singleDie, String winnerName, int totalTurns, List<Integer> rolls) {

    @Override
    public String toString() {
        return String.format("%s (%s) - Winner: %s in %d turns",
            variation, singleDie ? "Single Die" : "Double Dice", winnerName, totalTurns);
    }
}
