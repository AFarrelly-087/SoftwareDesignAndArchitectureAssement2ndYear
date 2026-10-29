package uk.ac.mmu.Game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.mmu.Game.applicationCode.usecase.newgame.Provided;
import uk.ac.mmu.Game.applicationCode.usecase.newgame.Usecase;
import uk.ac.mmu.Game.infrastructureCode.driven.FileResultAdapter;

@Configuration
public class AppConfig {

    @Bean
    public Provided newGameUsecase(FileResultAdapter fileResultAdapter) {
        return new Usecase(fileResultAdapter);
    }

    @Bean
    public uk.ac.mmu.Game.applicationCode.usecase.replaygame.Provided replayGameUsecase(FileResultAdapter fileResultAdapter) {
        return new uk.ac.mmu.Game.applicationCode.usecase.replaygame.Usecase(fileResultAdapter);
    }
}
