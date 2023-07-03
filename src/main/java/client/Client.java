package client;

import external.Service;

public class Client {
    private final Service dummyService;

    public Client(Service service) {
        this.dummyService = service;
    }

    //getIpProtocolNodeDefinition
    public void consume(String nodeDefinitionType) {
        switch (nodeDefinitionType) {
            case "getIpProtocol" -> dummyService.consume(input -> input.doSomething());
            case "getIpAddress" -> dummyService.consume(input -> input.doSomethingOther());
        }
//        dummyService.consume(input -> {
//            input.doSomething();
//        });
    }

    public void consumeWithOutput() {
        dummyService.consumeWithOutput(input -> input.doSomething());
    }

    public void supply() {
        dummyService.supply(() -> {
            return new Output();
        });
    }

    public void execute() {
        dummyService.execute(input -> {
            input.doSomething();
            return new Output();
        });
    }

    public Output process(Input input) {
        return dummyService.process(input, input2 -> {
            input2.doSomething();
            return new Output();
        });
    }
}
