:remote close
:remote connect tinkerpop.server conf/remote.yaml session-managed
:remote config timeout max
:remote console
system.graph('powertrain_graph').engine(Classic).ifNotExists().create()
:remote config alias g powertrain_graph.g
:load /tmp/Powertrain2/resources/graph/powertrain_graph.groovy
:remote close
