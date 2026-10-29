package uk.ac.mmu.Game.applicationCode.usecase.replaygame;

import uk.ac.mmu.Game.applicationCode.domainmodel.GameResult;
import java.util.List;

public interface Required {
    List<GameResult> getAllResults();
}
