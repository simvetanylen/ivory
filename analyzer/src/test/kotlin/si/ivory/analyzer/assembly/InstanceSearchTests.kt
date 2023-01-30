package si.ivory.analyzer.assembly

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import si.ivory.analyzer.example.ExampleAggregate
import si.ivory.analyzer.example.ExampleEvent
import si.ivory.analyzer.example.ExampleService1
import si.ivory.analyzer.example.ExampleService2
import si.ivory.analyzer.example.annotations.CommandHandler

class InstanceSearchTests {

    @Test
    fun test1() {
        val target = ExampleService1::class.java.canonicalName

        val types = target.toClassNode().methods.find {
            it.isAnnotatedWith(CommandHandler::class.java)
        }!!.getInstantiatedTypesRecursively("si/ivory")

        assertTrue(types.contains(ExampleEvent::class.java))
    }

    @Test
    fun test2() {
        val target = ExampleAggregate::class.java.canonicalName

        val types = target.toClassNode().methods.map {
            it.getInstantiatedTypesRecursively("si/ivory")
        }.flatten()

        assertTrue(types.contains(ExampleEvent::class.java))
    }

    @Test
    fun test3() {
        val target = ExampleService2::class.java.canonicalName

        val types = target.toClassNode().methods.find {
            it.isAnnotatedWith(CommandHandler::class.java)
        }!!.getInstantiatedTypesRecursively("si/ivory")

        assertTrue(types.contains(ExampleEvent::class.java))
    }
}