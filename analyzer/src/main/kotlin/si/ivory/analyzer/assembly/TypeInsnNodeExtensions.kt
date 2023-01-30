package si.ivory.analyzer.assembly

import org.objectweb.asm.tree.TypeInsnNode

fun TypeInsnNode.toCanonicalClassName(): String {
    return this.desc.replace("/", ".")
}
