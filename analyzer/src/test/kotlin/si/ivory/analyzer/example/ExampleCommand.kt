package si.ivory.analyzer.example

import si.ivory.analyzer.example.annotations.Command

@Command(
    aggregateName = "example-aggregate",
    commandName = "example-command"
)
data class ExampleCommand(
    val text: String,
)