[picodotdev@archlinux postgres]$ docker-compose up
docker exec -it postgres_postgres_1 bash
psql -U sa
CREATE DATABASE app;
\l
\c app
\dn
\dt
\dt gradle.*
\d+ gradle.item
\q
DROP DATABASE app;
