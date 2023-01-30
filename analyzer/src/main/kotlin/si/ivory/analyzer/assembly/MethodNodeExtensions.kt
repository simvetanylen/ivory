package si.ivory.analyzer.assembly

import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.TypeInsnNode


fun MethodNode.instantiatedTypes(): List<Class<*>> {
    return this.instructions.mapNotNull {
        if (it is TypeInsnNode) {
            Class.forName(it.toCanonicalClassName())
        } else {
            null
        }
    }
}

inline fun <reified T> MethodNode.findInstructions(): List<T> {
    return this.instructions.filter {
        it is T
    } as List<T>
}

fun MethodNode.parameterTypes(): List<Class<*>> {
    return this.parameters?.mapNotNull { parameterNode ->
        val localVariable = this.localVariables?.find {
            it.name == parameterNode.name
        }
        try {
            localVariable?.getClass()
        } catch (t: Throwable) {
            throw RuntimeException("Unable to get variable name for ${this.name}, ${this.desc}", t)
        }
    } ?: listOf()
}

fun MethodNode.getInstantiatedTypesRecursively(ownerPrefix: String, depth: Int = 4): List<Class<*>> {
    val allMethodNodes = listOf(this) + this.getAllChildMethodNodes(ownerPrefix, depth)

    return allMethodNodes.flatMap {
        it.instantiatedTypes()
    }
}

fun MethodNode.getAllChildMethodNodes(ownerPrefix: String, depth: Int = 4): List<MethodNode> {
    val directChildren = this.findInstructions<MethodInsnNode>()
        .mapNotNull { it.resolveMethodNode(ownerPrefix) }

    if (depth == 0) {
        return directChildren
    }

    val nextChildren = directChildren.flatMap { it.getAllChildMethodNodes(ownerPrefix, depth - 1) }

    return directChildren + nextChildren
}

fun MethodNode.isAnnotatedWith(clazz: Class<*>): Boolean {
    return this.visibleAnnotations?.find {
        it.getClass() == clazz
    } != null
}
