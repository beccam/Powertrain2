wget https://github.com/brianmhess/cassandra-loader/releases/download/v0.0.20/cassandra-loader
chmod +x cassandra-loader
./cassandra-loader -delim "|" -dateFormat "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"  -f silkconfig -host localhost -schema 'silkconfig.silkconfig("_id","_index", "_score","_source", "_type","_version",defaultindex,found)'
