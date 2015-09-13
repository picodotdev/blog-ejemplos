#!/usr/bin/env bash
BASEDIR=$(dirname $0)
liquibase --classpath=$BASEDIR/misc/database/postgresql-9.4-1202.jdbc41.jar --driver=org.postgresql.Driver --changeLogFile=$BASEDIR/misc/database/changelog.xml --url=jdbc:postgresql://localhost:5432/app --username=sa --password=sa $1
