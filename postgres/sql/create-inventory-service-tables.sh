#!/bin/bash
set -e

PGPASSWORD=inventory_service_password psql -v ON_ERROR_STOP=1 --username "inventory_service_user" --dbname "inventory_service_db" <<-EOSQL

	CREATE TABLE item (
		id BIGINT PRIMARY KEY,
		name VARCHAR(128) NOT NULL,
		code VARCHAR(128) NOT NULL UNIQUE,
		NUMERIC INT NOT NULL,
		quantity int NOT NULL
	);

	CREATE SEQUENCE hibernate_sequence;

EOSQL
