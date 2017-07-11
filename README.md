# seasame-rio-api

Modified [Sesame](https://bitbucket.org/openrdf/sesame) API with added support for [Yet Another RDF Serialization](https://www.researchgate.net/publication/309695477_RDF_Data_in_Property_Graph_Model) (YARS) serialization.

## Example YARS file

```
(bjf52cgbj8vcou3{v:'<http://example.com/text>'})
(92fqdijn7c79w3d{v:'example type'})
(bjf52cgbj8vcou3)-[http://example.com/type]->(92fqdijn7c79w3d)
```

## See also
* [neo4j-sparql-extension-yars](https://github.com/lszeremeta/neo4j-sparql-extension-yars) - Neo4j [unmanaged extension](http://docs.neo4j.org/chunked/stable/server-unmanaged-extensions.html)
for [RDF](http://www.w3.org/TR/rdf-primer/) storage and
[SPARQL 1.1 query](http://www.w3.org/TR/sparql11-protocol/) features with support for YARS serialization,
* [sesame-rio-yars](https://github.com/lszeremeta/sesame-rio-yars) - YARS parser for Sesame,
* [ttl-to-yars](https://github.com/lszeremeta/ttl-to-yars) - simple [Turtle](https://www.w3.org/TR/turtle/) to YARS converter
* [yars-samples](https://github.com/lszeremeta/yars-samples) - ready to use sample YARS files

## Author
Copyright (C) 2017 [Łukasz Szeremeta](https://github.com/lszeremeta). All rights reserved.

Copyright (C) 2001-2013 [Aduna](http://www.aduna-software.com/). All rights reserved.

Distributed under standard [Sesame license](https://github.com/lszeremeta/sesame-rio-api/blob/master/LICENSE).
