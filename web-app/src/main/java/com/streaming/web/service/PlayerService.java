package com.streaming.web.service;

import com.streaming.core.model.Player;
import com.streaming.core.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public Flux<Player> findAll() {
        return playerRepository.findAll();
    }

    public Mono<Player> save(Player player) {
        return playerRepository.save(player);
    }

    public Mono<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public Mono<Void> deleteById(Long id) {
        return playerRepository.deleteById(id);
    }
}
