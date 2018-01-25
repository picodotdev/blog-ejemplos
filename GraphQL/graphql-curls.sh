# Docker
docker-compose up
docker-compose run --rm -p 8090:8080 app bash

---
# Consulta

### Query
curl -s "http://localhost:8090/library?query=\{books\{title\}\}" | jq

### Only requested data
curl -s "http://localhost:8090/library?query=\{books\{title+date\}\}" | jq
curl -s "http://localhost:8090/library?query=\{books\{title+date+author\{name\}\}\}" | jq

### One request multiple queries
curl -s "http://localhost:8090/library?query=\{books\{title+date\}authors\{name\}\}" | jq

### Verbs: first GET, second POST
curl -vs "http://localhost:8090/library?query=\{books\{title+date\}authors\{id+name\}\}" | jq
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title}}"}' http://localhost:8080/library | jq

### On POST request multiple queries
curl -s -XPOST -H "Content-Type: application/json" -d '[{"query": "query Books{books{title}authors{name}}"}]' http://localhost:8090/library | jq
curl -s -XPOST -H "Content-Type: application/json" -d '[{"query": "query Books{books{title}}"}, {"query": "query Authors{authors{name}}"}]' http://localhost:8090/library | jq

---
# Mutación

### Mutation
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes", "author": 6 }}' http://localhost:8090/library | jq
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '[{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "Xxx", "author": 6}}, {"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "Xxx", "author": 6 }}]' http://localhost:8090/library | jq

### Mutación y consulta
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '[{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "Xxx", "author": 6}}, {"query": "query Books{books{title}}"}]' http://localhost:8090/library | jq

---

# Resolver
curl -s "http://localhost:8090/library?query=\{books\{title+isbn\}\}" | jq

# Query with arguments, resolver with arguments
curl -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^[OR].*\"}){title}}"}' http://localhost:8090/library | jq

# Scalar
curl -s "http://localhost:8090/library?query=\{books\{title+date\}\}" | jq

# Pagination
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^Ready.*\"}){title comments{edges{node{text}cursor} pageInfo{startCursor endCursor hasNextPage}}}}"}' http://localhost:8090/library | jq
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^Ready.*\"}){title comments(limit:3){edges{node{text}cursor} pageInfo{startCursor endCursor hasNextPage}}}}"}' http://localhost:8090/library | jq
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^Ready.*\"}){title comments(limit:3,after:\"aW8uZ2l0aHViLnBpY29kb3RkZXYuYmxvZ2JpdGl4LmdyYXBocWwuQ29tbWVudDoz\"){edges{node{text}cursor} pageInfo{startCursor endCursor hasNextPage}}}}"}' http://localhost:8090/library | jq

# Exception, mensajes personalizados
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes 2", "author": 999}}' http://localhost:8090/library | jq
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes 2", "author": 999}}' http://localhost:8090/library | jq
curl -s -XPOST -H "Content-Type: application/json" -H "User: hacker" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes 2", "author": 999}}' http://localhost:8090/library | jq

# Batched
curl -s "http://localhost:8090/library?query=\{books\{title+date+isbn\}\}" | jq
curl -s "http://localhost:8090/library?query=\{books\{title+date+batchedIsbn\}\}" | jq
TODO, ISBN con timeout

# Status codes
curl -sv "http://localhost:8090/library?query=\{books\{title+author\{nombre\}\}\}" | jq