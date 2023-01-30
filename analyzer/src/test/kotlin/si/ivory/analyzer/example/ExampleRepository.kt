package si.ivory.analyzer.example

import java.util.UUID

interface ExampleRepository {
    fun get(id: UUID): ExampleAggregate
    fun save(aggregate: ExampleAggregate)
}