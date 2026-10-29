package uk.ac.mmu.Game.applicationCode.usecase.newgame;

import uk.ac.mmu.Game.applicationCode.domainmodel.*;

import java.util.List;
import java.util.Map;

public class Usecase implements Provided {

    private final Required required;

    public Usecase(Required required) {
        this.required = required;
    }

    @Override
    public void execute(GameVariation variation, boolean singleDie) {
        int boardSize = GameFactory.getBoardSize(variation);
        int[] positions = GameFactory.createPositions(boardSize);
        List<Player> players = GameFactory.createPlayers(variation, positions);

        DiceShaker diceShaker;
        if (variation == GameVariation.HIT_RULE_FIXED) {
            diceShaker = new FixedDiceShaker(List.of(5, 12, 7, 1, 7, 1, 12));
        } else if (variation == GameVariation.WORMHOLE_FIXED) {
            diceShaker = new FixedDiceShaker(List.of(4, 1, 10));
        } else {
            diceShaker = singleDie ? new RandomSingleDiceShaker() : new RandomDoubleDiceShaker();
        }

        Map<Integer, Integer> wormholes = GameFactory.createWormholes(variation, boardSize);
        boolean exactEnd = GameFactory.isExactEnd(variation);
        boolean hitRule = GameFactory.isHitRule(variation);
        
        GameSimulation simulation = new GameSimulation(players, diceShaker, exactEnd, hitRule, wormholes, boardSize, singleDie ? 1 : 2, variation);
        GameResult result = simulation.run();
        if (result != null) {
            required.saveResult(result);
        }
    }
}
