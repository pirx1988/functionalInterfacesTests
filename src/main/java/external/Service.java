package external;

import client.Input;
import client.Output;
import io.vavr.Function1;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Service {

    void consume(Consumer<Input> consume);

    void supply(Supplier<Output> supplier);

    void execute(Function<Input, Output> function);

    Output process(Input input, Function<Input, Output> function);

    void consumeWithOutput(Function1<Input, Output> consume);

}
