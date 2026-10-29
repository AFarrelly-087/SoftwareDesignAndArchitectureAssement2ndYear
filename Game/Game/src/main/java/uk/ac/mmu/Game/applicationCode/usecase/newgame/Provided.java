package uk.ac.mmu.Game.applicationCode.usecase.newgame;

import uk.ac.mmu.Game.applicationCode.domainmodel.GameVariation;

public interface Provided {
    void execute(GameVariation variation, boolean singleDie);
}
