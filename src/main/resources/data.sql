INSERT IGNORE INTO account(account_id, fk_account_role_id, fk_account_status_id, email, password, created_at, deleted_at)
VALUES
    (1, 2, 2, "admin@candidocorp.com", "", now(), null);

INSERT IGNORE INTO user
    (user_id, fk_account_id, fk_gender_id, fk_address_id, first_name, last_name, birth_date, mobile_number, phone_number, last_modified_name, created_at, deleted_at)
VALUES
    (1, 1, 1, null, "John", "Doe", "1998-04-15", "3481138394", "3458483720", null, now(), null);


INSERT IGNORE INTO application_form (
    application_form_id,
    fk_account_id,
    display_name,
    notes,
    max_applicants,
    application_form_body,
    url_code,
    start_date,
    end_date,
    created_at,
    updated_at,
    deleted_at,
    feedback_publication_date,
    feedback_expiration_date
) VALUES
(
    -- Prima riga
    1,            -- AUTO_INCREMENT
    1,               -- fk_account_id
    'Modulo di iscrizione',            -- display_name
    'Note iniziali del modulo di iscrizione', -- notes
    10,              -- max_applicants
    'Corpo del form 1',               -- application_form_body
    'ABCD',          -- url_code (4 caratteri)
    '2024-01-01 09:00:00', -- start_date
    '2024-01-10 18:00:00', -- end_date
    CURRENT_TIMESTAMP,     -- created_at
    CURRENT_TIMESTAMP,     -- updated_at
    NULL, -- deleted_at
    '2024-02-01 09:00:00', -- feedback_publication_date
    '2024-03-01 09:00:00'  -- feedback_expiration_date
),
(
    -- Seconda riga
    2,
    1,
    'Modulo di feedback',
    'Note sul modulo di feedback',
    5,
    'Corpo del form 2',
    'EFGH',
    '2024-02-01 09:00:00',
    '2024-02-15 17:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL, -- deleted_at
    '2024-03-01 09:00:00',
    '2024-04-01 09:00:00'
),
(
    -- Terza riga
    3,
    2,  -- esempio di altro account
    'Modulo avanzato',
    'Note sul modulo avanzato',
    20,
    'Corpo del form 3',
    'WXYZ',
    '2024-03-01 00:00:00',
    '2024-03-15 23:59:59',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL, -- deleted_at
    '2024-05-01 00:00:00',
    '2024-06-01 00:00:00'
);

INSERT IGNORE INTO application (
    application_id,
    fk_account_id,
    fk_application_form_id,
    fk_application_status_id,
    filled_body,
    progress,
    created_at,
    updated_at,
    deleted_at,
    sent_at
) VALUES
(
    1,       -- AUTO INCREMENT
    1,          -- ID account (come richiesto)
    1,          -- Collegato al primo form inserito
    1,          -- Esempio di status
    'Testo compilato per la prima application',
    50,         -- progress (esempio)
    CURRENT_TIMESTAMP,
    NULL,
    NULL,
    CURRENT_TIMESTAMP
),
(
    2,
    1,
    2,          -- Collegato al secondo form inserito
    2,          -- Esempio di status
    'Testo compilato per la seconda application',
    100,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);