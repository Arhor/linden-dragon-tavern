package org.arhor.diploma.commons;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

abstract class AbstractLazy<T> implements Lazy<T> {

    private Supplier<T> source;

    protected boolean computed;

    @Nullable
    protected T value;

    AbstractLazy(@Nonnull final Supplier<T> source) {
        Objects.requireNonNull(source, "Lazy evaluation source must not be null");
        this.source = source;
    }

    @Override
    public final boolean isComputed() {
        return computed;
    }

    protected final T compute() {
        if (!computed) {
            value = source.get();
            // Nullifying source reference allows to release
            // associated scope and prevent memory leaks
            source = null;
            computed = true;
        }
        return value;
    }
}
