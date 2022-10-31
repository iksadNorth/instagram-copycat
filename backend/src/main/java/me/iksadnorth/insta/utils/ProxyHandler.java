package me.iksadnorth.insta.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class ProxyHandler<E> {
    private final E entity;

    public ProxyHandler(E entity) {
        this.entity = entity;
    }

    public static <E> ProxyHandler<E> of(E entity) {
        return new ProxyHandler<>(entity);
    }

    public static <E> ProxyHandler<E> empty() {
        return new ProxyHandler<>(null);
    }

    public Boolean isProxy() {
        return isProxy(entity);
    }

    public static <E> Boolean isProxy(E entity) {
        return Optional.ofNullable(entity)
                .map(Object::getClass)
                .map(Class::getName)
                .map(String::toLowerCase)
                .filter(s -> s.contains("hibernateproxy"))
                .isPresent();
    }

    public Boolean isNull() {
        return entity == null;
    }

    public <D> ProxyHandler<D> map(Function<? super E, ? extends D> mapper) {
        Objects.requireNonNull(mapper);
        return isNull() || isProxy() ? empty() : of(mapper.apply(this.entity));
    }

    public E orElse(E other) {
        return isNull() || isProxy() ? other : this.entity;
    }
}
