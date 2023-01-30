package si.ivory.analyzer.assembly

import org.objectweb.asm.tree.LocalVariableNode

fun LocalVariableNode.getClass(): Class<*> {
    return TypeReader.read(desc)
}
