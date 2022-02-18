#!/bin/bash
set -e

PGPASSWORD=shipping_service_password psql -v ON_ERROR_STOP=1 --username "shipping_service_user" --dbname "shipping_service_db" <<-EOSQL

	CREATE TABLE shipping_order (
		id BIGINT PRIMARY KEY,
		origin VARCHAR(128) NOT NULL,
		destination VARCHAR(128) NOT NULL UNIQUE,
		placed_at TIMESTAMP NOT NULL
	);

	CREATE SEQUENCE hibernate_sequence;

EOSQL
