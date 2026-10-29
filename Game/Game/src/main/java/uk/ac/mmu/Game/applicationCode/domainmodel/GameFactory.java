package uk.ac.mmu.Game.applicationCode.domainmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameFactory {

    public static int getBoardSize(GameVariation variation) {
        return (variation == GameVariation.LARGE_BOARD) ? 6 : 5;
    }

    public static int[] createPositions(int boardSize) {
        int[] positions = new int[boardSize * boardSize];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = i + 1;
        }
        return positions;
    }

    public static List<Player> createPlayers(GameVariation variation, int[] positions) {
        List<Player> players = new ArrayList<>();


        if (variation == GameVariation.LARGE_BOARD) {
            PlayerFactory greenFactory = new GreenPlayerFactory();
            PlayerFactory yellowFactory = new YellowPlayerFactory();
            PlayerFactory blueFactory = new BluePlayerFactory();
            PlayerFactory redFactory = new RedPlayerFactory();
            players.add(blueFactory.create(0, 30, positions));
            players.add(redFactory.create(30, 0, positions));
            players.add(greenFactory.create(5, 35, positions));
            players.add(yellowFactory.create(35, 5, positions));
        }
        else{
            PlayerFactory blueFactory = new BluePlayerFactory();
            PlayerFactory redFactory = new RedPlayerFactory();
            players.add(blueFactory.create(0, 24, positions));
            players.add(redFactory.create(24, 0, positions));
        }
        return players;
    }

    public static Map<Integer, Integer> createWormholes(GameVariation variation, int boardSize) {
        Map<Integer, Integer> wormholes = new HashMap<>();
        if (variation == GameVariation.WORMHOLE || variation == GameVariation.WORMHOLE_FIXED) {
                wormholes.put(5, 15); wormholes.put(15, 5);
        }
        return wormholes;
    }

    public static boolean isExactEnd(GameVariation variation) {
        return variation == GameVariation.EXACT_END;
    }

    public static boolean isHitRule(GameVariation variation) {
        return variation == GameVariation.HIT_RULE || variation == GameVariation.HIT_RULE_FIXED;
    }
}
