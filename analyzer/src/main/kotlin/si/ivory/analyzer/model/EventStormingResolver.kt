package si.ivory.analyzer.model

import org.objectweb.asm.tree.MethodNode
import si.ivory.analyzer.assembly.TypeReader
import si.ivory.analyzer.assembly.getInstantiatedTypesRecursively
import si.ivory.analyzer.assembly.toClassNode

class EventStormingResolver(
    private val methodSelector: (MethodNode) -> Boolean,
    private val nodeFactory: (Class<*>) -> EventStormingNode?,
) {

    fun resolveClasses(ownerPrefix: String, classNames: List<String>): Map<EventStormingNode, List<EventStormingNode>> {
        val resultMap = mutableMapOf<EventStormingNode, List<EventStormingNode>>()

        classNames.forEach { className ->
            resolveClass(ownerPrefix, className).forEach {
                if (resultMap[it.key] == null) {
                    resultMap[it.key] = listOf()
                }

                resultMap[it.key] = (resultMap[it.key]!! + it.value).distinct()
            }
        }

        return resultMap
    }

    fun resolveClass(ownerPrefix: String, className: String): Map<EventStormingNode, List<EventStormingNode>> {
        return className.toClassNode().methods.mapNotNull {
            resolveMethod(ownerPrefix, it)
        }.toMap()
    }

    fun resolveMethod(ownerPrefix: String, method: MethodNode): Pair<EventStormingNode, List<EventStormingNode>>? {
        val inputs = if (methodSelector(method)) {
            TypeReader.readMethodParams(method.desc).mapNotNull { parameterType ->
                nodeFactory(parameterType)
            }
        } else {
            return null
        }

        if (inputs.size > 1) {
            throw RuntimeException("More than 1 input")
        }

        if (inputs.isEmpty()) {
            return null
        }

        val instantiatedTypes = method.getInstantiatedTypesRecursively(ownerPrefix)

        val outputs = instantiatedTypes.mapNotNull { instantiatedType ->
            nodeFactory(instantiatedType)
        }

        return inputs.first() to outputs
    }
}