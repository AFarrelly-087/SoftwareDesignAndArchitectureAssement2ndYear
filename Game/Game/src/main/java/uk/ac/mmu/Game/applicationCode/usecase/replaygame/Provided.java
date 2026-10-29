package uk.ac.mmu.Game.applicationCode.usecase.replaygame;

import uk.ac.mmu.Game.applicationCode.domainmodel.GameResult;
import java.util.List;

public interface Provided {
    List<GameResult> getAvailableGames();
    void replay(GameResult gameToReplay);
}
