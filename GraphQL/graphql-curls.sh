# Docker
docker-compose up
docker-compose run --rm -p 8090:8080 app bash

---
# Consulta

### Query
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title}}"}' http://localhost:8080/graphql | jq

### Only requested data
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date}}"}' http://localhost:8080/graphql | jq
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date author{name}}}"}' http://localhost:8080/graphql | jq

### One request multiple queries
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title} authors{name}}"}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -d '[{"query": "query Books{books{title}}"}, {"query": "query Authors{authors{name}}"}]' http://localhost:8080/graphql | jq

---
# Mutación

### Mutation
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes", "author": 6 }}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '[{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "Xxx", "author": 6}}, {"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "Xxx", "author": 6 }}]' http://localhost:8080/graphql | jq

### Mutación y consulta
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '[{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "Xxx", "author": 6}}, {"query": "query Books{books{title}}"}]' http://localhost:8080/graphql | jq

---

# Resolver
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title isbn}}"}' http://localhost:8080/graphql | jq

# Query with arguments, resolver with arguments
curl -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^[OR].*\"}){title}}"}' http://localhost:8080/graphql | jq

# Scalar
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date}}"}' http://localhost:8080/graphql | jq

# Pagination
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^Ready.*\"}){title comments{edges{node{text}cursor} pageInfo{startCursor endCursor hasNextPage}}}}"}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^Ready.*\"}){title comments(limit:3){edges{node{text}cursor} pageInfo{startCursor endCursor hasNextPage}}}}"}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^Ready.*\"}){title comments(limit:3,after:\"aW8uZ2l0aHViLnBpY29kb3RkZXYuYmxvZ2JpdGl4LmdyYXBocWwuQ29tbWVudDoz\"){edges{node{text}cursor} pageInfo{startCursor endCursor hasNextPage}}}}"}' http://localhost:8080/graphql | jq

# Exception, mensajes personalizados
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes 2", "author": 999}}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes 2", "author": 999}}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -H "User: hacker" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes 2", "author": 999}}' http://localhost:8080/graphql | jq

# Batched
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date isbn}}"}' http://localhost:8080/graphql | jq
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date batchedIsbn}}"}' http://localhost:8080/graphql | jq
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date dataLoaderIsbn}}"}' http://localhost:8080/graphql | jq

# Status codes
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title author {nombre}}}"}' http://localhost:8080/graphql | jq