package si.ivory.analyzer.assembly

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import si.ivory.analyzer.example.ExampleCommand

class TypeReaderTests {

    @Test
    fun test1() {
        val desc = "(Ljava/lang/Object;Ljava/lang/String;)V"
        val params = TypeReader.readMethodParams(desc)
        assertTrue(params == listOf(
            Object::class.java,
            String::class.java
        ))
    }

    @Test
    fun test2() {
        val desc = "()Ljava/util/UUID;"
        val params = TypeReader.readMethodParams(desc)
        assertTrue(params == listOf<Class<*>>())
    }

    @Test
    fun test3() {
        val desc = "(Lsi/ivory/analyzer/example/ExampleCommand;)V"
        val params = TypeReader.readMethodParams(desc)
        assertTrue(params == listOf(
            ExampleCommand::class.java
        ))
    }
}