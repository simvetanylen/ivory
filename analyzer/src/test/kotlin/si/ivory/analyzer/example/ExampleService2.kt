package si.ivory.analyzer.example

import si.ivory.analyzer.example.annotations.CommandHandler
import java.util.*

class ExampleService2(
    private val eventPublisher: EventPublisher,
    private val repository: ExampleRepository,
) {

    @CommandHandler
    fun handle(id: UUID, command: ExampleCommand) {
        val aggregate = repository.get(id)
        aggregate.apply(command)
        aggregate.events.forEach {
            eventPublisher.publish(it)
        }
        repository.save(aggregate)
    }
}