package si.ivory.analyzer.example.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DomainEvent(
    val aggregateName: String,
    val eventName: String,
)
