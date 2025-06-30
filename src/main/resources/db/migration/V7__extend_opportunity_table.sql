-- V7__extend_opportunity_table.sql

ALTER TABLE opportunity
    -- Public description of the opportunity
    ADD COLUMN description TEXT,

    -- Compensation details
    ADD COLUMN compensation_amount NUMERIC(10, 2),
    ADD COLUMN compensation_note VARCHAR(255),
    ADD COLUMN fk_currency_id BIGINT REFERENCES currency(currency_id),

    -- Location information
    ADD COLUMN location VARCHAR(255),
    ADD COLUMN fk_location_type_id BIGINT REFERENCES location_type(location_type_id),

    -- Classification
    ADD COLUMN fk_opportunity_type_id BIGINT REFERENCES opportunity_type(opportunity_type_id),
    ADD COLUMN fk_opportunity_status_id BIGINT REFERENCES opportunity_status(opportunity_status_id),
    ADD COLUMN fk_opportunity_level_id BIGINT REFERENCES opportunity_level(opportunity_level_id);
