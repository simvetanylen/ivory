package si.ivory.analyzer.example.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command(
    val aggregateName: String,
    val commandName: String,
)
