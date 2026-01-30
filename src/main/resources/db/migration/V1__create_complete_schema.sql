-- Create PostGIS extension
CREATE EXTENSION IF NOT EXISTS postgis;

-- Create users table
CREATE TABLE users (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Create stores table with PostGIS location column
CREATE TABLE stores (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    city VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    street VARCHAR(255) NOT NULL,
    street2 VARCHAR(255),
    street3 VARCHAR(255),
    address_name VARCHAR(255) NOT NULL,
    longitude DECIMAL(10, 6) NOT NULL,
    latitude DECIMAL(10, 6) NOT NULL,
    complex_number VARCHAR(20),
    sap_store_id VARCHAR(20),
    show_warning_message BOOLEAN NOT NULL DEFAULT false,
    today_open VARCHAR(20),
    today_close VARCHAR(20),
    location_type VARCHAR(50),
    collection_point BOOLEAN NOT NULL DEFAULT false,
    location geography(Point, 4326)
);

-- Create indexes
CREATE INDEX idx_stores_city ON stores(city);
CREATE INDEX idx_stores_location ON stores USING GIST (location);

-- Insert test user
INSERT INTO users (name, email, password, role) 
VALUES ('Test User', 'test@test.com', '$2a$12$OtKUR3CgkZKiA/buFfTQo.YGRt9wDEDYlJ.3YbYlMg3YQPwIlpR9K', 'USER');
