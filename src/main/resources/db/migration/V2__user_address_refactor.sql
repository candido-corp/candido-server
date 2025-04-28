-- Drop foreign key constraint from user first
ALTER TABLE candido.user DROP CONSTRAINT IF EXISTS user_fk_address_id;

-- Drop old address table
DROP TABLE IF EXISTS candido.address;

-- Create function to auto-update updated_at
CREATE
OR REPLACE FUNCTION on_update_current_timestamp_address()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at
= now();
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

-- Create address table again with correct column order
CREATE TABLE candido.address
(
    address_id         BIGSERIAL PRIMARY KEY,
    fk_territory_id    BIGINT  NOT NULL,
    fk_address_type_id BIGINT  NOT NULL,
    fk_user_id         INTEGER NOT NULL,
    is_primary         BOOLEAN     DEFAULT FALSE,
    zip                VARCHAR(255),
    street             VARCHAR(255),
    house_number       VARCHAR(255),
    created_at         TIMESTAMPTZ DEFAULT current_timestamp,
    updated_at         TIMESTAMPTZ DEFAULT current_timestamp,
    deleted_at         TIMESTAMPTZ,
    CONSTRAINT fk_address_territory FOREIGN KEY (fk_territory_id) REFERENCES candido.territory (territory_id),
    CONSTRAINT fk_address_type FOREIGN KEY (fk_address_type_id) REFERENCES candido.address_type (address_type_id),
    CONSTRAINT fk_address_user FOREIGN KEY (fk_user_id) REFERENCES candido.user (user_id)
);

-- Create trigger to auto-update updated_at
CREATE TRIGGER tr_update_address_updated_at
    BEFORE UPDATE
    ON candido.address
    FOR EACH ROW
    EXECUTE FUNCTION on_update_current_timestamp_address();

-- Drop xref_user_address table if it exists
DROP TABLE IF EXISTS candido.xref_user_address;

-- Drop function on_update_current_timestamp_user_address if it exists
DROP FUNCTION IF EXISTS on_update_current_timestamp_user_address();

-- Drop fk_address_id column from user table
ALTER TABLE candido.user DROP COLUMN IF EXISTS fk_address_id;
