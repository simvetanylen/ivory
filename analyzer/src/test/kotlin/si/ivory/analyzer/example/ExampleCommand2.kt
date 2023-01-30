package si.ivory.analyzer.example

import si.ivory.analyzer.example.annotations.Command

@Command(
    aggregateName = "example-aggregate-2",
    commandName = "example-command-2"
)
data class ExampleCommand2(
    val text: String,
)