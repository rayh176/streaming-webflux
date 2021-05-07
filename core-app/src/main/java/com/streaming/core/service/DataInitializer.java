package com.streaming.core.service;

import com.streaming.core.model.Player;
import com.streaming.core.repos.PlayerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

/** Alternative approach to data loading **/
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class DataInitializer implements ApplicationRunner {
    @NonNull
    private final PlayerRepository playerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("start data initialization...");
        this.playerRepository
                .saveAll(
                        List.of(
                                Player.builder().name("one").age(1).build(),
                                Player.builder().name("two").age(2).build(),
                                Player.builder().name("three").age(3).build(),
                                Player.builder().name("four").age(4).build(),
                                Player.builder().name("five").age(5).build(),
                                Player.builder().name("six").age(6).build(),
                                Player.builder().name("seven").age(7).build(),
                                Player.builder().name("eight").age(8).build(),
                                Player.builder().name("nine").age(9).build(),
                                Player.builder().name("ten").age(10).build()
                        )
                )
                .thenMany(
                        this.playerRepository.findAll()
                )
                .subscribe((data) -> log.info("player:" + data),
                        (err) -> log.error("error" + err),
                        () -> log.info("initialization is done...")
                );
    }
}
