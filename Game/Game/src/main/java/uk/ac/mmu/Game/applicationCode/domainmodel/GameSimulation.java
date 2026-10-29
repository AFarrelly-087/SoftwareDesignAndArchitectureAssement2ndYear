package uk.ac.mmu.Game.applicationCode.domainmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameSimulation {
    private final List<Player> players;
    private final DiceShaker diceShaker;
    private final boolean exactEnd;
    private final boolean hitRule;
    private final Map<Integer, Integer> wormholes;
    private final int boardSize;
    private final int diceCount;
    private final GameVariation variation;

    public GameSimulation(List<Player> players, DiceShaker diceShaker, boolean exactEnd, boolean hitRule, Map<Integer, Integer> wormholes, int boardSize, int diceCount, GameVariation variation) {
        this.players = players;
        this.diceShaker = diceShaker;
        this.exactEnd = exactEnd;
        this.hitRule = hitRule;
        this.wormholes = wormholes;
        this.boardSize = boardSize;
        this.diceCount = diceCount;
        this.variation = variation;
    }

    public GameResult run() {
        System.out.println("Board size: " + boardSize + "x" + boardSize);
        System.out.println("Number of players: " + players.size());
        
        StringBuilder rulesList = new StringBuilder();
        if (exactEnd) rulesList.append("End ");
        if (hitRule) rulesList.append("Hit ");
        if (!wormholes.isEmpty()) rulesList.append("Wormhole");
        String rulesStr = rulesList.toString().trim().replace(" ", ", ");
        
        String defaultRule = (variation == GameVariation.LARGE_BOARD) ? "Large Board" : "Basic";
        System.out.println("Rules: " + (rulesStr.isEmpty() ? defaultRule : rulesStr));
        System.out.println("Dice used: " + diceCount);
        System.out.println();

        boolean gameWon = false;
        int currentPlayerIndex = 0;
        Player winner = null;
        List<Integer> recordedRolls = new ArrayList<>();

        while (!gameWon && diceShaker.hasNext()) {
            Player currentPlayer = players.get(currentPlayerIndex);
            int roll = diceShaker.next();
            recordedRolls.add(roll);
            currentPlayer.incrementTurnCount();
            
            int beforePos = currentPlayer.getPosition();
            int beforeIndex = currentPlayer.getCurrentIndex();
            
            int direction = currentPlayer.getDirection();
            int targetIndex = beforeIndex + (direction * roll);
            int endIndex = currentPlayer.getEND_INDEX();
            
            if (exactEnd) {
                boolean overshoot = (direction == 1 && targetIndex > endIndex) || 
                                   (direction == -1 && targetIndex < endIndex);
                if (overshoot) {
                    int excess = Math.abs(targetIndex - endIndex);
                    targetIndex = endIndex - (direction * excess);
                }
            } else {
                if (direction == 1 && targetIndex > endIndex) targetIndex = endIndex;
                else if (direction == -1 && targetIndex < endIndex) targetIndex = endIndex;
            }
            
            boolean canMove = true;
            if (hitRule) {
                for (Player other : players) {
                    if (other != currentPlayer && other.getCurrentIndex() == targetIndex) {
                        canMove = false;
                        break;
                    }
                }
            }
            
            if (canMove) {
                currentPlayer.setCurrentIndex(targetIndex);
                
                int posAfterMove = currentPlayer.getPosition();
                if (wormholes.containsKey(posAfterMove)) {
                    int wormholeToPos = wormholes.get(posAfterMove);
                    int wormholeToIndex = currentPlayer.findIndexForPosition(wormholeToPos);
                    if (wormholeToIndex != -1) {
                        currentPlayer.setCurrentIndex(wormholeToIndex);
                    }
                }
            }
            
            System.out.println("Player: " + currentPlayer.getPlayerName());
            System.out.println("Roll: " + roll);
            System.out.println("Before: Position " + beforePos);
            System.out.println("After: " + currentPlayer);
            System.out.println("Turns: " + currentPlayer.getTurnCount());
            System.out.println();
            
            if (currentPlayer.isAtEnd()) {
                gameWon = true;
                winner = currentPlayer;
            } else {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            }
        }

        if (winner != null) {
            System.out.println("Winning Player: " + winner.getPlayerName());
            int totalTurns = 0;
            for (Player p : players) {
                totalTurns += p.getTurnCount();
            }
            System.out.println("Total turns: " + totalTurns);
            return new GameResult(variation, diceCount == 1, winner.getPlayerName(), totalTurns, recordedRolls);
        }
        return null;
    }
}
