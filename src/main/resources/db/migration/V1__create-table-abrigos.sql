CREATE TABLE shelters (
    id BIGSERIAL PRIMARY KEY,
    shelter_name VARCHAR(100) NOT NULL UNIQUE,
    shelter_phone_number VARCHAR(14) NOT NULL UNIQUE,
    shelter_email VARCHAR(100) NOT NULL UNIQUE
);
