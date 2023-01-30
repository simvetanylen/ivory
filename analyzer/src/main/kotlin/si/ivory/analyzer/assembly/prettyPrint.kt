package si.ivory.analyzer.assembly

import org.objectweb.asm.tree.*


fun prettyPrint(node: AbstractInsnNode) {
    when (node) {
        is LabelNode -> println("Label label=${node.label};")
        is VarInsnNode -> println("Var var=${node.`var`};")
        is LdcInsnNode -> println("Ldc cst=${node.cst};")
        is MethodInsnNode -> println("Method name=${node.name};desc=${node.desc};owner=${node.owner};itf=${node.itf};")
        is LineNumberNode -> println("LineNumber line=${node.line};start=${node.start};")
        is TypeInsnNode -> println("Type desc=${node.desc};")
        is InsnNode -> println("Insn type=${node.type};")
        is FieldInsnNode -> println("Field name=${node.name};desc=${node.desc};owner=${node.owner};")
        is FrameNode -> println("Frame type=${node.type};local=${node.local};stack=${node.stack};")
        is JumpInsnNode -> println("Jump label=${node.label};")
        else -> throw RuntimeException("Unknown insn ${node.type} ${node::class.java}")
    }
}
