package si.ivory.analyzer.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.objectweb.asm.tree.MethodNode
import si.ivory.analyzer.assembly.isAnnotatedWith
import si.ivory.analyzer.example.ExampleService1
import si.ivory.analyzer.example.ExampleService4
import si.ivory.analyzer.example.ExampleSideEffects
import si.ivory.analyzer.example.annotations.Command
import si.ivory.analyzer.example.annotations.CommandHandler
import si.ivory.analyzer.example.annotations.DomainEvent
import si.ivory.analyzer.example.annotations.DomainEventHandler

class EventStormingResolverTests {

    @Test
    fun test1() {
        val result = EventStormingResolver(
            methodSelector = this::methodSelector,
            nodeFactory = this::nodeFactory
        ).resolveClass(prefix, ExampleService1::class.java.canonicalName)

        assertTrue(
            result == mapOf(
                EventStormingNode(
                    aggregate = "example-aggregate",
                    name = "example-command",
                    type = EventStormingNode.Type.COMMAND
                ) to listOf(
                    EventStormingNode(
                        aggregate = "example-aggregate",
                        name = "example-event",
                        type = EventStormingNode.Type.EVENT
                    )
                )
            )
        )
    }

    @Test
    fun test2() {
        val classes = listOf(
            ExampleService4::class.java.canonicalName,
            ExampleSideEffects::class.java.canonicalName
        )

        val result = EventStormingResolver(
            methodSelector = this::methodSelector,
            nodeFactory = this::nodeFactory
        ).resolveClasses(prefix, classes)

        println(result)

        assertTrue(
            result == mapOf(
                EventStormingNode(
                    aggregate = "example-aggregate",
                    name = "example-command",
                    type = EventStormingNode.Type.COMMAND
                ) to listOf(
                    EventStormingNode(
                        aggregate = "example-aggregate",
                        name = "example-event",
                        type = EventStormingNode.Type.EVENT
                    )
                ),
                EventStormingNode(
                    aggregate = "example-aggregate",
                    name = "example-event",
                    type = EventStormingNode.Type.EVENT
                ) to listOf(
                    EventStormingNode(
                        aggregate = "example-aggregate-2",
                        name = "example-command-2",
                        type = EventStormingNode.Type.COMMAND
                    )
                )
            )
        )
    }

    private val prefix = "si/ivory/analyzer/example"

    private fun methodSelector(method: MethodNode): Boolean {
        return method.isAnnotatedWith(CommandHandler::class.java)
                || method.isAnnotatedWith(DomainEventHandler::class.java)
    }

    private fun nodeFactory(type: Class<*>): EventStormingNode? {
        val commandAnnotation = type.annotations.find {
            it.annotationClass == Command::class
        } as Command?

        if (commandAnnotation != null) {
            return EventStormingNode(
                aggregate = commandAnnotation.aggregateName,
                type = EventStormingNode.Type.COMMAND,
                name = commandAnnotation.commandName
            )
        }

        val eventAnnotation = type.annotations.find {
            it.annotationClass == DomainEvent::class
        } as DomainEvent?

        if (eventAnnotation != null) {
            return EventStormingNode(
                aggregate = eventAnnotation.aggregateName,
                type = EventStormingNode.Type.EVENT,
                name = eventAnnotation.eventName
            )
        }

        return null
    }
}