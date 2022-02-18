#!/bin/bash
set -e

PGPASSWORD=account_service_password psql -v ON_ERROR_STOP=1 --username "account_service_user" --dbname "account_service_db" <<-EOSQL

	CREATE TABLE account (
		id BIGINT PRIMARY KEY,
		name VARCHAR(128) NOT NULL,
		email VARCHAR(128) NOT NULL UNIQUE,
		created_at TIMESTAMP NOT NULL
	);

	CREATE SEQUENCE hibernate_sequence;

EOSQL
