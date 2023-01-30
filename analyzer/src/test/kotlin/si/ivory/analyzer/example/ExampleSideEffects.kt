package si.ivory.analyzer.example

import si.ivory.analyzer.example.annotations.DomainEventHandler

class ExampleSideEffects(
    private val commandProducer: CommandProducer,
) {

    @DomainEventHandler
    fun handle(event: ExampleEvent) {
        commandProducer.produce(ExampleCommand2(
            text = event.text
        ))
    }
}