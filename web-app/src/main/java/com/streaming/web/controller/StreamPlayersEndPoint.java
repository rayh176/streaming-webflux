package com.streaming.web.controller;

import com.streaming.core.model.Player;
import com.streaming.web.service.PlayerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/v1/liquidity")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class StreamPlayersEndPoint {
    @NonNull
    private final PlayerService playerService;

    @GetMapping
    public Flux<Player> findAll() {
        return this.playerService.findAll();
    }
    @PostMapping("")
    public Mono<Player> create(@RequestBody Player player) {
        return this.playerService.save(player);
    }
    @GetMapping("/{id}")
    public Mono<Player> get(@PathVariable("id") Long id) {
        return this.playerService.findById(id);
    }
    @PutMapping("/{id}")
    public Mono<Player> update(@PathVariable("id") Long id, @RequestBody Player player) {
        return this.playerService.findById(id)
                .map(p -> {
                    p.setName(player.getName());
                    p.setAge(player.getAge());
                    return p;
                })
                .flatMap(p -> this.playerService.save(p));
    }
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Long id) {
        return this.playerService.deleteById(id);
    }
}