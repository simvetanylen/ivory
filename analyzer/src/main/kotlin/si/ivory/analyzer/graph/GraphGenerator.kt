package si.ivory.analyzer.graph

import guru.nidi.graphviz.attribute.Font
import guru.nidi.graphviz.attribute.Rank
import guru.nidi.graphviz.attribute.Shape
import guru.nidi.graphviz.attribute.Style
import guru.nidi.graphviz.engine.Engine
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.graph
import guru.nidi.graphviz.toGraphviz
import si.ivory.analyzer.model.EventStormingNode
import java.io.File

object GraphGenerator {

    fun generate(eventStorm: Map<EventStormingNode, List<EventStormingNode>>, targetAggregate: String, output: File) {
        val direct = eventStorm.mapNotNull {
            if (it.key.aggregate == targetAggregate) {
                Pair(it.key, it.value)

            } else {
                val filtered = it.value.filter {
                    it.aggregate == targetAggregate
                }

                if (filtered.size > 0) {
                    Pair(it.key, filtered)
                } else {
                    null
                }
            }
        }

        val allNodes = direct.map {
            listOf(it.first) + it.second
        }.flatten()

        if (allNodes.isEmpty()) {
            return
        }

        graph(directed = true, name = "main") {
            graph[Rank.dir(Rank.RankDir.LEFT_TO_RIGHT)]
            node[Font.name("Helvetica,Arial,sans-serif"), Style.FILLED]
            edge[Font.name("Helvetica,Arial,sans-serif")]

            allNodes.map {
                if (it.aggregate == targetAggregate) {
                    val label = it.name
                    val color = when (it.type) {
                        EventStormingNode.Type.COMMAND -> "#a7ccf6"
                        EventStormingNode.Type.EVENT -> "#fc9d49"
                    }
                    it.ref()["fillcolor" eq color, Shape.BOX, "label" eq label]
                } else {
                    val label = it.aggregate + "/" + it.name
                    val color = "#eca2c3"
                    it.ref()["fillcolor" eq color, Shape.BOX, "label" eq label]
                }
            }

            direct.map { first ->
                first.second.map { second ->
                    first.first.ref() - second.ref()
                }
            }.flatten()
        }.toGraphviz()
            .engine(Engine.DOT)
            .render(Format.PNG)
            .toFile(output)
    }

    private fun EventStormingNode.ref(): String {
        return aggregate + "_" + name
    }


}

