package si.ivory.analyzer.example

import si.ivory.analyzer.example.annotations.DomainEvent

@DomainEvent(
    aggregateName = "example-aggregate",
    eventName = "example-event",
)
data class ExampleEvent(
    val text: String,
)