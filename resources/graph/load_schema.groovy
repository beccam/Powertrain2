system.graph('powertrain_graph').ifNotExists().create()
:remote config alias g powertrain_graph.g
:load /tmp/powertrain-workshop/Powertrain2/resources/graph/powertrain_graph.groovy
