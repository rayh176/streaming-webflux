package com.streaming.web.controller;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseController<T,R> {

    protected abstract Function<T,R> getMapper();

    private Class<T> getType() {
        return (Class<T>)( ((ParameterizedType) this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }

    protected Mono<R> buildMono(Supplier<T> supplier) {
        return Mono.fromCallable(supplier::get).map(getMapper()).subscribeOn(Schedulers.boundedElastic());
    }

    protected Mono<R> buildMonoOrThrow(Supplier<Optional<T>> supplier,
                                       Supplier<Exception> exceptionSupplier) {
        return Mono.fromCallable(() -> supplier.get().map(getMapper()).orElseThrow(exceptionSupplier)).subscribeOn(Schedulers.boundedElastic());
    }

    protected Mono<Page<R>> buildPageMono(Supplier<Page<T>> supplier) {
        return Mono.fromCallable(() -> supplier.get().map(getMapper())).subscribeOn(Schedulers.boundedElastic());
    }

    protected Flux<R> buildFlux(Supplier<Flux<T>> supplier) {
        return supplier.get().map(getMapper());
    }

    protected <I> Supplier<Exception> notFoundException (I id) { return () -> new RuntimeException(getType() +", "+id);}
}
