package com.streaming.core.repos;

import com.streaming.core.model.Player;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

@NoRepositoryBean
public interface PlayerRepository extends ReactiveCrudRepository<Player, Long> {
    @Query("select id, name, age from player where name = $1")
    Flux<Player> findAllByName(String name);

    @Query("select * from player where age = $1")
    Flux<Player> findByAge(Long age);
}
