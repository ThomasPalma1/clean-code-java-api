CREATE TABLE pets (
    id BIGSERIAL PRIMARY KEY,
    pet_type VARCHAR(100) NOT NULL,
    pet_name VARCHAR(100) NOT NULL,
    pet_breed VARCHAR(100) NOT NULL,
    pet_age INT NOT NULL,
    pet_color VARCHAR(100) NOT NULL,
    pet_weight DECIMAL(6,2) NOT NULL,
    pet_is_adopted BOOLEAN NOT NULL,
    shelter_id BIGINT NOT NULL,
    CONSTRAINT fk_pets_shelter_id FOREIGN KEY (shelter_id) REFERENCES shelters(id)
);
