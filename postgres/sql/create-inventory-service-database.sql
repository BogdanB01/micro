CREATE USER inventory_service_user with encrypted password 'inventory_service_password';
CREATE DATABASE inventory_service_db;
GRANT ALL PRIVILEGES ON DATABASE inventory_service_db to inventory_service_user;