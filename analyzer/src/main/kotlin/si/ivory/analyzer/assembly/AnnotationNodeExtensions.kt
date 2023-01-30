package si.ivory.analyzer.assembly

import org.objectweb.asm.tree.AnnotationNode

fun AnnotationNode.getClass(): Class<*> {
    val className = this.desc.removePrefix("L")
        .removeSuffix(";")
        .replace("/", ".")

    return Class.forName(className)
}
