package client;

import external.Service;
import io.vavr.Function1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class ClientTest {

    @Captor
    private ArgumentCaptor<Consumer<Input>> consumerArgumentCaptor;

    @Captor
    private ArgumentCaptor<Function1<Input, Output>> consumerWithOutputArgumentCaptor;

    @Test
    void consumeCallsServiceAndDoSomethingWithInput() {

        // given
        Service service = Mockito.mock(Service.class);
        Input input = Mockito.mock(Input.class);
        Client client = new Client(service);

        //when
        client.consume("getIpProtocol");

        // then
        Mockito.verify(service).consume(consumerArgumentCaptor.capture());
        Consumer<Input> consumer = consumerArgumentCaptor.getValue();
        consumer.accept(input);
        Mockito.verify(input).doSomething();
    }

    @Test
        // using mockito answers
    void consumeCallsServiceAndDoSomethingWithInput2() {
        // given
        Service service = Mockito.mock(Service.class);
        Input input = Mockito.mock(Input.class);

        // providing fake implementation of the consume method
        Mockito.doAnswer(invocation -> {
            Consumer<Input> consumer = invocation.getArgument(0);
            // invoke consumer
            consumer.accept(input);
            return null;
        }).when(service).consume(Mockito.any());

        Client client = new Client(service);

        // when
        client.consume("getIpProtocol");

        // then
        Mockito.verify(service).consume(Mockito.any());
        Mockito.verify(input).doSomething();
    }

    @Test
        // using mockito answers
    void consumeCallsServiceAndDoSomethingOtherWithInput2() {
        // given
        Service service = Mockito.mock(Service.class);
        Input input = Mockito.mock(Input.class);

        // providing fake implementation of the consume method
        Mockito.doAnswer(invocation -> {
            Consumer<Input> consumer = invocation.getArgument(0);
            // invoke consumer
            consumer.accept(input);
            return null;
        }).when(service).consume(Mockito.any());

        Client client = new Client(service);

        // when
        client.consume("getIpAddress");

        // then
        Mockito.verify(service).consume(Mockito.any());
        Mockito.verify(input).doSomethingOther();
    }

    @Test
    void consumeWithOutputCallsServiceAndDoSomethingWithInput() {

        // given
        Service service = Mockito.mock(Service.class);
        Input input = Mockito.mock(Input.class);
        // create client with service dependency
        Client client = new Client(service);

        //when
        client.consumeWithOutput();

        // then
        Mockito.verify(service, times(1)).consumeWithOutput(consumerWithOutputArgumentCaptor.capture());
        Function1<Input, Output> consumer = consumerWithOutputArgumentCaptor.getValue();
        consumer.apply(input);
        // could be replaced by
        Mockito.verify(input, times(1)).doSomething();
    }

    @Test
    void supplyCallServiceAndProvidesItOutput() {
        //given
        Service service = Mockito.mock();
        AtomicReference<Output> outputReference = new AtomicReference<>();
        Mockito.doAnswer(invocation -> {
            Supplier<Output> supplier = invocation.getArgument(0);
            Output output = supplier.get();
            outputReference.set(output);
            return null;
        }).when(service).supply(Mockito.any());

        Client client = new Client(service);

        //when
        client.supply();

        //then
        Mockito.verify(service).supply(Mockito.any());
        assertNotNull(outputReference.get());
    }
}