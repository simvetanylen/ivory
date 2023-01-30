package si.ivory.analyzer.assembly

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import si.ivory.analyzer.example.*
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

    @Test
    fun test4() {
        val target = ExampleService3::class.java.canonicalName

        val types = target.toClassNode().methods.find {
            it.isAnnotatedWith(CommandHandler::class.java)
        }!!.getInstantiatedTypesRecursively("si/ivory")

        assertTrue(types.contains(ExampleEvent::class.java))
    }

    @Test
    fun test5() {
        val target = ExampleService4::class.java.canonicalName

        val types = target.toClassNode().methods.find {
            it.isAnnotatedWith(CommandHandler::class.java)
        }!!.getInstantiatedTypesRecursively("si/ivory")

        assertTrue(types.contains(ExampleEvent::class.java))
    }
}