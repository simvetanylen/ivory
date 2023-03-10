package si.ivory.analyzer.example

import si.ivory.analyzer.example.annotations.CommandHandler
import java.util.*

class ExampleService3(
    private val eventPublisher: EventPublisher,
    private val repository: ExampleRepository,
) {

    @CommandHandler
    fun handle(id: UUID, command: ExampleCommand) = mutate(id) {
        eventPublisher.publish(ExampleEvent(text = command.text))
    }

    private fun mutate(id: UUID, mutation: (state: ExampleAggregate) -> Unit) {
        val aggregate = repository.get(id)
        mutation(aggregate)
        repository.save(aggregate)
        aggregate.events.forEach {
            eventPublisher.publish(it)
        }
    }
}