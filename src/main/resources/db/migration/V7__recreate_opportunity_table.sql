DROP TABLE IF EXISTS opportunity CASCADE;

CREATE TABLE opportunity (
    opportunity_id BIGSERIAL PRIMARY KEY,

    -- Owner
    fk_account_id BIGINT NOT NULL REFERENCES account(account_id),

    -- Identifiers
    display_name VARCHAR(128) NOT NULL,
    url_code VARCHAR(4) NOT NULL,

    -- Configuration
    form_schema JSONB NOT NULL,
    max_applicants BIGINT,

    -- Content
    description TEXT,
    notes VARCHAR(255),

    -- Compensation (all optional)
    compensation_amount NUMERIC(10,2),
    compensation_note VARCHAR(255),
    fk_currency_id BIGINT REFERENCES currency(currency_id),

    -- Location & classification
    fk_territory_id BIGINT NOT NULL REFERENCES territory(territory_id),
    fk_location_type_id BIGINT NOT NULL REFERENCES location_type(location_type_id),
    fk_opportunity_type_id BIGINT NOT NULL REFERENCES opportunity_type(opportunity_type_id),
    fk_opportunity_status_id BIGINT NOT NULL REFERENCES opportunity_status(opportunity_status_id),
    fk_opportunity_level_id BIGINT NOT NULL REFERENCES opportunity_level(opportunity_level_id),

    -- Dates
    start_date TIMESTAMPTZ,
    end_date TIMESTAMPTZ,
    feedback_publication_date TIMESTAMPTZ,
    feedback_expiration_date TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ,
    deleted_at TIMESTAMPTZ
);
