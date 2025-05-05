CREATE TABLE pet_owners (
    id BIGSERIAL PRIMARY KEY,
    pet_owner_Name VARCHAR(100) NOT NULL,
    pet_owner_phone_number VARCHAR(14) NOT NULL UNIQUE,
    pet_owner_email VARCHAR(100) NOT NULL UNIQUE
);
