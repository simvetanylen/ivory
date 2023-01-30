package si.ivory.analyzer.model

data class EventStormingNode(
    val aggregate: String,
    val name: String,
    val type: Type
) {
    enum class Type {
        COMMAND,
        EVENT,
    }
}