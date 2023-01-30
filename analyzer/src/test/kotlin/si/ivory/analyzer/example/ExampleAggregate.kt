package si.ivory.analyzer.example

class ExampleAggregate {

    val events = mutableListOf<Any>()

    fun apply(command: ExampleCommand) {
        events += ExampleEvent(text = command.text)
    }
}