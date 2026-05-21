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

# Aliases
curl -s -XPOST -H "Content-Type: application/json" -d '[{"query": "query Libros{libros:books{titulo:title}}"}]' http://localhost:8080/graphql | jq

### Multiple result types
curl -s -XPOST -H "Content-Type: application/json" -d '[{"query": "query Publications {publications {... on Book {__typename title} ... on Magazine {__typename name}}}"}]' http://localhost:8080/graphql | jq

---

# Resolver
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title isbn}}"}' http://localhost:8080/graphql | jq

# Batched/Dataloader
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date isbn}}"}' http://localhost:8080/graphql | jq
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date batchedIsbn}}"}' http://localhost:8080/graphql | jq
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date dataLoaderIsbn}}"}' http://localhost:8080/graphql | jq

---

# Query with arguments, variables and default variables
curl -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^[OR].*\"}){title}}"}' http://localhost:8080/graphql | jq
curl -XPOST -H "Content-Type: application/json" -d '{"query": "query Books($regexp: String){books(filter:{title:$regexp}){title}}", "variables": {"regexp": "^[OR].*"}}' http://localhost:8080/graphql | jq
curl -XPOST -H "Content-Type: application/json" -d '{"query": "query Books($regexp: String = \"^[OR].*\"){books(filter:{title:$regexp}){title}}", "variables": {}}' http://localhost:8080/graphql | jq
curl -XPOST -H "Content-Type: application/json" -d '{"query": "query Books($regexp: String = \"^[OR].*\"){books(filter:{title:$regexp}){title}}", "variables": {"regexp": "^[E].*"}}' http://localhost:8080/graphql | jq

---

# Mutación

### Mutation
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes", "author": "6" }}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '[{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "Xxx", "author": "6"}}, {"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "Xxx", "author": "6" }}]' http://localhost:8080/graphql | jq

### Mutación y consulta
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '[{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "Xxx", "author": 6}}, {"query": "query Books{books{title}}"}]' http://localhost:8080/graphql | jq

---

# Exception, mensajes personalizados
curl -s -XPOST -H "Content-Type: application/json" -H "User: admin" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes 2", "author": "999"}}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes 2", "author": "6"}}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -H "User: hacker" -d '{"query": "mutation AddBook($title: String, $author: Long){addBook(title: $title, author: $author){title}}", "variables": { "title": "El lazarillo de Tormes 2", "author": "6"}}' http://localhost:8080/graphql | jq

---

# Directive
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books($withAuthor: Boolean!){books{title date author @include(if: $withAuthor){name}}}", "variables": {"withAuthor": false}}' http://localhost:8080/graphql | jq

# Fragment
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "fragment BookFragment on Book {title date} query Books{books{...BookFragment}}"}' http://localhost:8080/graphql | jq

# Instrospection, Meta Fields
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{__typename title}}"}' http://localhost:8080/graphql | jq
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Types{__schema {types {name}}}"}' http://localhost:8080/graphql | jq
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Types{__type(name: \"Book\") {name fields{name}}}"}' http://localhost:8080/graphql | jq

# Scalar
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title date}}"}' http://localhost:8080/graphql | jq

---

# Pagination
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^Ready.*\"}){title comments{edges{node{text}cursor} pageInfo{startCursor endCursor hasNextPage}}}}"}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^Ready.*\"}){title comments(limit:\"3\"){edges{node{text}cursor} pageInfo{startCursor endCursor hasNextPage}}}}"}' http://localhost:8080/graphql | jq
curl -s -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books(filter:{title:\"^Ready.*\"}){title comments(limit:\"3\",after:\"aW8uZ2l0aHViLnBpY29kb3RkZXYuYmxvZ2JpdGl4LmdyYXBocWwuQ29tbWVudDoz\"){edges{node{text}cursor} pageInfo{startCursor endCursor hasNextPage}}}}"}' http://localhost:8080/graphql | jq

# Status codes
curl -vs -XPOST -H "Content-Type: application/json" -d '{"query": "query Books{books{title author {name}}}"}' http://localhost:8080/graphql | jq