package uk.ac.mmu.Game.infrastructureCode.driving;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import uk.ac.mmu.Game.applicationCode.domainmodel.GameVariation;
import uk.ac.mmu.Game.applicationCode.usecase.newgame.Provided;

import java.util.Scanner;

@Component
@Profile("game")
public class Runner implements CommandLineRunner {

    private final Provided newGameUsecase;
    private final uk.ac.mmu.Game.applicationCode.usecase.replaygame.Provided replayGameUsecase;

    @Autowired
    public Runner(Provided newGameUsecase,
                  uk.ac.mmu.Game.applicationCode.usecase.replaygame.Provided replayGameUsecase) {
        this.newGameUsecase = newGameUsecase;
        this.replayGameUsecase = replayGameUsecase;
    }

    @Override
    public void run(String...args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Play Basic Game");
            System.out.println("2. Play Exact End ");
            System.out.println("3. Play Hit Rule ");
            System.out.println("4. Play Wormhole ");
            System.out.println("5. Play Large Board  (6x6 board)");
            System.out.println("6. Showcase Hit  ");
            System.out.println("7. Showcase Wormhole");
            System.out.println("8. Replay a past game");
            System.out.println("9. Exit");
            System.out.print("Enter your choice (1-9): ");

            if (!scanner.hasNextLine()) break;
            String choice = scanner.nextLine().trim();

            if (choice.equals("9")) {
                break;
            }

            if (choice.equals("8")) {
                handleReplay(scanner);
                continue;
            }

            GameVariation variation;
            switch (choice) {
                case "1":
                    variation = GameVariation.BASIC;
                    break;
                case "2":
                    variation = GameVariation.EXACT_END;
                    break;
                case "3":
                    variation = GameVariation.HIT_RULE;
                    break;
                case "4":
                    variation = GameVariation.WORMHOLE;
                    break;
                case "5":
                    variation = GameVariation.LARGE_BOARD;
                    break;
                case "6":
                    variation = GameVariation.HIT_RULE_FIXED;
                    break;
                case "7":
                    variation = GameVariation.WORMHOLE_FIXED;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
                    continue;
            }

            boolean singleDie = false;
            boolean validDiceChoice = variation == GameVariation.HIT_RULE_FIXED || variation == GameVariation.WORMHOLE_FIXED;
            while(!validDiceChoice){
                System.out.print("Use single die? (y/n, default is double dice): ");
                String input = scanner.nextLine().trim().toLowerCase();
                if(input.equals("y")){
                    singleDie = true;
                    validDiceChoice = true;
                } else if(input.equals("n") || input.isEmpty()){
                    validDiceChoice = true;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            }


            System.out.println("\nPlaying: " + variation + " (" + (singleDie ? "Single Die" : "Double Dice") + ")...");
            newGameUsecase.execute(variation, singleDie);
        }

        System.out.println("You chose to exit");
    }

    private boolean handleReplay(Scanner scanner) {
        var games = replayGameUsecase.getAvailableGames();
        if (games.isEmpty()) {
            System.out.println("No saved games found to replay.");
            return false;
        }

        System.out.println("\n Saved Games: ");
        for (int i = 0; i < games.size(); i++) {
            System.out.println((i + 1) + ". " + games.get(i).toString());
        }
        System.out.print("Enter game number to replay (or 0 to cancel): ");
        
        String input = scanner.nextLine().trim();
        try {
            int gameChoice = Integer.parseInt(input);
            if (gameChoice > 0 && gameChoice <= games.size()) {
                replayGameUsecase.replay(games.get(gameChoice - 1));
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
        return false;
    }
}
