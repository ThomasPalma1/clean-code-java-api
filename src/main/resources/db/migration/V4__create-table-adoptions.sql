CREATE TABLE adoptions (
    id BIGSERIAL PRIMARY KEY,
    data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pet_owner_id BIGINT NOT NULL,
    pet_id BIGINT NOT NULL,
    reason VARCHAR(255),
    status VARCHAR(100) NOT NULL,
    justification_status VARCHAR(255),
    CONSTRAINT fk_adoptions_pet_owner_id FOREIGN KEY (pet_owner_id) REFERENCES pet_owners(id),
    CONSTRAINT fk_adoptions_pet_id FOREIGN KEY (pet_id) REFERENCES pets(id)
);
