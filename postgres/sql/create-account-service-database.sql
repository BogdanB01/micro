CREATE USER account_service_user with encrypted password 'account_service_password';
CREATE DATABASE account_service_db;
GRANT ALL PRIVILEGES ON DATABASE account_service_db to account_service_user;