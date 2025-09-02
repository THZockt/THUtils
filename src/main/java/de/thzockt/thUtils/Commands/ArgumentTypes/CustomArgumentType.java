package de.thzockt.thUtils.Commands.ArgumentTypes;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@NullMarked
public interface CustomArgumentType<T, N> extends ArgumentType<T> {

    /*
    |
    | This is some shitty code that is unfinished and needed for the minigame instance type which is also unfinished
    |
    */

    @Override
    T parse(final StringReader reader) throws CommandSyntaxException;

    T convert(N nativeType) throws CommandSyntaxException;

    default <S> T convert(final N nativeType, final S source) throws CommandSyntaxException {
        return this.convert(nativeType);
    }

    @Override
    default <S> T parse(final StringReader reader, final S source) throws CommandSyntaxException {
        return ArgumentType.super.parse(reader, source);
    }

    ArgumentType<N> getNativeType();

    @Override
    @ApiStatus.NonExtendable
    default Collection<String> getExamples() {
        return this.getNativeType().getExamples();
    }

    @Override
    default <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        return ArgumentType.super.listSuggestions(context, builder);
    }
}