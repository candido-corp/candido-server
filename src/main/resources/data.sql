INSERT IGNORE INTO account(fk_account_role_id, fk_account_status_id, email, password, created_at, deleted_at)
VALUES
    (2, 2, "admin@candidocorp.com", "", now(), null);

INSERT IGNORE INTO user
    (fk_account_id, fk_gender_id, fk_address_id, first_name, last_name, birth_date, mobile_number, phone_number, last_modified_name, created_at, deleted_at)
VALUES
    (1, 1, null, "John", "Doe", "1998-04-15", "3481138394", "3458483720", null, now(), null);

SET @accountId = (SELECT account_id FROM account WHERE email = "admin@candidocorp.com");

INSERT IGNORE INTO application_form (
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
    @accountId,               -- fk_account_id
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
    @accountId,
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
    @accountId,
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

SET @formIdOne = (SELECT application_form_id FROM application_form WHERE url_code = "ABCD");
SET @formIdTwo = (SELECT application_form_id FROM application_form WHERE url_code = "EFGH");
SET @formIdThree = (SELECT application_form_id FROM application_form WHERE url_code = "WXYZ");

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
    1,        -- AUTO INCREMENT
    @accountId,        -- ID account
    @formIdOne,        -- Collegato al primo form
    1,        -- Esempio di status
    '{}',
    50,
    '2023-05-13 10:15:00',  -- created_at
    '2023-05-14 11:20:00',  -- updated_at
    NULL,                   -- deleted_at
    NULL   -- sent_at
),
(
    2,
    @accountId,
    @formIdTwo,
    2,
    '{}',
    100,
    '2023-09-22 09:30:00',   -- created_at
    '2024-01-10 14:45:00',   -- updated_at
    NULL,
    '2024-01-10 14:45:00'
),
(
    3,
    @accountId,
    @formIdThree,
    3,
    '{}',
    100,
    '2024-03-01 09:00:00',   -- created_at
    '2024-03-02 10:00:00',   -- updated_at
    NULL,
    '2024-03-02 10:00:00'
),
(
    4,
    @accountId,
    @formIdOne,
    4,
    '{}',
    100,
    '2024-07-15 08:00:00',   -- created_at
    '2024-07-15 15:00:00',   -- updated_at
    NULL,
    '2024-07-15 15:00:00'
),
(
    5,
    @accountId,
    @formIdTwo,
    5,
    '{}',
    98,
    '2025-01-28 07:00:00',   -- created_at
    '2025-01-29 08:00:00',   -- updated_at
    NULL,
    NULL
);
