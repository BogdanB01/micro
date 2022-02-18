CREATE USER shipping_service_user with encrypted password 'shipping_service_password';
CREATE DATABASE shipping_service_db;
GRANT ALL PRIVILEGES ON DATABASE shipping_service_db to shipping_service_user;