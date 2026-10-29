package uk.ac.mmu.Game.applicationCode.usecase.replaygame;

import uk.ac.mmu.Game.applicationCode.domainmodel.*;
import java.util.List;
import java.util.Map;

public class Usecase implements Provided {

    private final Required required;

    public Usecase(Required required) {
        this.required = required;
    }

    @Override
    public List<GameResult> getAvailableGames() {
        return required.getAllResults();
    }

    @Override
    public void replay(GameResult gameToReplay) {
        GameVariation variation = gameToReplay.variation();
        boolean singleDie = gameToReplay.singleDie();
        
        int boardSize = GameFactory.getBoardSize(variation);
        int[] positions = GameFactory.createPositions(boardSize);
        List<Player> players = GameFactory.createPlayers(variation, positions);

        DiceShaker diceShaker = new FixedDiceShaker(gameToReplay.rolls());

        Map<Integer, Integer> wormholes = GameFactory.createWormholes(variation, boardSize);
        boolean exactEnd = GameFactory.isExactEnd(variation);
        boolean hitRule = GameFactory.isHitRule(variation);

        System.out.println("\nGame Loaded: ");
        GameSimulation simulation = new GameSimulation(players, diceShaker, exactEnd, hitRule, wormholes, boardSize, singleDie ? 1 : 2, variation);
        simulation.run();
        System.out.println("Game Finished.");
    }
}
