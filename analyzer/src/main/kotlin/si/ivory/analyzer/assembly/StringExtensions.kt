package si.ivory.analyzer.assembly

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

fun String.toClassNode(): ClassNode {
    val reader = ClassReader(this)
    val classNode = ClassNode()

    reader.accept(classNode, 0)

    return classNode
}
