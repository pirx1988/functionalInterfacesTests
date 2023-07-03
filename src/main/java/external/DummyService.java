package external;

import client.Input;
import client.Output;
import io.vavr.Function1;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DummyService implements Service{
    @Override
    public void consume(Consumer<Input> consume) {

    }

    @Override
    public void supply(Supplier<Output> supplier) {

    }

    @Override
    public void execute(Function<Input, Output> function) {

    }

    @Override
    public Output process(Input input, Function<Input, Output> function) {
        return null;
    }

    @Override
    public void consumeWithOutput(Function1<Input, Output> consume) {
    }
}
