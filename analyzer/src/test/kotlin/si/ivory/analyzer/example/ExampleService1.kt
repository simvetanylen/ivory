package si.ivory.analyzer.example

import si.ivory.analyzer.example.annotations.CommandHandler

class ExampleService1(
    private val eventPublisher: EventPublisher,
) {

    @CommandHandler
    fun hande(command: ExampleCommand) {
        val event = ExampleEvent(text = command.text)
        eventPublisher.publish(event)
    }
}