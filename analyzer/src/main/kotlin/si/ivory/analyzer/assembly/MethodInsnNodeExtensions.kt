package si.ivory.analyzer.assembly

import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode

fun MethodInsnNode.resolveMethodNode(ownerPrefix: String): MethodNode? {
    if (!this.owner.startsWith(ownerPrefix)) {
        return null
    }

    val methods = this.owner.toClassNode().methods.filter {
        try {
            it.name == this.name && it.desc == this.desc
        } catch (t: Throwable) {
            throw RuntimeException("name=${this.name}, desc=${this.desc}, owner=${this.owner}", t)
        }
    }

    if (methods.size > 1) {
        throw RuntimeException("${methods.size} found for name=${this.name}, desc=${this.desc}, owner=${this.owner}")
    }

    return methods.firstOrNull()
}

fun MethodInsnNode.getParameterTypes(): List<Class<*>> {
    try {
        return TypeReader.readMethodParams(this.desc)
    } catch (t: Throwable) {
        throw RuntimeException("Unable to read parameters for method : ${this.name}", t)
    }
}

fun MethodInsnNode.ownerClassNode(): ClassNode {
    return this.owner.toClassNode()
}