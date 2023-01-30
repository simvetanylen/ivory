package si.ivory.analyzer.assembly

import org.objectweb.asm.Type

// Params are between ()
// Arrays starts by [
// Primitive are only one letter
// Object refs starts with L
// Also check https://stackoverflow.com/questions/67952646/how-to-get-count-and-types-of-method-parameters-using-org-objectweb-asm-methodvi
object TypeReader {

    fun readMethodParams(desc: String): List<Class<*>> {
        try {
            return Type.getMethodType(desc).argumentTypes.map {
                it.toClass()
            }
        } catch (t: Throwable) {
            throw RuntimeException("Desc unreadable : $desc", t)
        }
    }

    fun read(str: String): Class<*> {
        return Type.getType(str).toClass()
    }

    private fun Type.toClass(): Class<*> {
        if (this.toString().startsWith("[")) {
            return Class.forName(this.toString().replace("/", "."))
        }
        return when (this.className) {
            "long" -> Long::class.java
            "int" -> Int::class.java
            "boolean" -> Boolean::class.java
            "double" -> Double::class.java
            else -> Class.forName(this.className)
        }
    }
}