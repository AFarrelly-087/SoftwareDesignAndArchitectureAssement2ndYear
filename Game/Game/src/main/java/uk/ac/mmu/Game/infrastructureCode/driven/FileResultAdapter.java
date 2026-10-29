package uk.ac.mmu.Game.infrastructureCode.driven;

import org.springframework.stereotype.Component;
import uk.ac.mmu.Game.applicationCode.domainmodel.GameResult;
import uk.ac.mmu.Game.applicationCode.domainmodel.GameVariation;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileResultAdapter implements uk.ac.mmu.Game.applicationCode.usecase.newgame.Required,
        uk.ac.mmu.Game.applicationCode.usecase.replaygame.Required {

    private static final String FILE_NAME = "game_results.txt";

    @Override
    public void saveResult(GameResult result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(serialize(result));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving game result: " + e.getMessage());
        }
    }

    @Override
    public List<GameResult> getAllResults() {
        List<GameResult> results = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return results;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    results.add(deserialize(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading game results: " + e.getMessage());
        }
        return results;
    }

    private String serialize(GameResult result) {
        String rolls = result.rolls().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        
        return String.join("|",
                result.variation().name(),
                String.valueOf(result.singleDie()),
                result.winnerName(),
                String.valueOf(result.totalTurns()),
                rolls
        );
    }

    private GameResult deserialize(String line) {
        String[] parts = line.split("\\|", -1);
        GameVariation variation = GameVariation.valueOf(parts[0]);
        boolean singleDie = Boolean.parseBoolean(parts[1]);
        String winnerName = parts[2];
        int totalTurns = Integer.parseInt(parts[3]);
        
        List<Integer> rolls = new ArrayList<>();
        if (parts.length > 4 && !parts[4].isEmpty()) {
            rolls = Arrays.stream(parts[4].split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        
        return new GameResult(variation, singleDie, winnerName, totalTurns, rolls);
    }
}
