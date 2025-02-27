-- ---------------------------------------------------------------------
-- 1) Insert into 'account' using ON CONFLICT DO NOTHING
-- ---------------------------------------------------------------------
INSERT INTO candido.account (
    fk_account_role_id,
    fk_account_status_id,
    email,
    password,
    created_at,
    deleted_at
)
VALUES
    (2, 2, 'admin@candidocorp.com',  '', now(), null),
    (2, 2, 'client@candidocorp.com', '', now(), null)
ON CONFLICT (email) DO NOTHING;


-- ---------------------------------------------------------------------
-- 2) Procedura anonima PL/pgSQL:
--    - Recupera gli ID degli account appena inseriti
--    - Inserisce record nelle altre tabelle collegate
-- ---------------------------------------------------------------------
DO $$
    DECLARE
        accountId_admin   bigint;
        accountId_client  bigint;
        formIdOne         bigint;
        formIdTwo         bigint;
        formIdThree       bigint;
    BEGIN
        -- 2.1) Fetch account IDs into variables
        SELECT account_id
        INTO accountId_admin
        FROM candido.candido.account
        WHERE email = 'admin@candidocorp.com';

        SELECT account_id
        INTO accountId_client
        FROM candido.candido.account
        WHERE email = 'client@candidocorp.com';


        -- 2.2) Insert into "user"
        --      NB: "user" è tra doppi apici perché "user" è una parola riservata in SQL
        INSERT INTO candido."user" (
            fk_account_id,
            fk_gender_id,
            fk_address_id,
            first_name,
            last_name,
            birth_date,
            mobile_number,
            phone_number,
            last_modified_name,
            created_at,
            deleted_at
        )
        VALUES
            (accountId_admin,  1, NULL, 'Admin',  'Doe',   '1998-04-15', '12345678',    '',           NULL, now(), NULL),
            (accountId_client, 1, NULL, 'Client', 'Ino',   '1975-10-22', '999999999',   '3458483720', NULL, now(), NULL)
        ON CONFLICT DO NOTHING;


        -- 2.3) Insert into application_form
        INSERT INTO candido.application_form (
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
        )
        VALUES
            (
                accountId_admin,
                'Modulo di iscrizione',
                'Note iniziali del modulo di iscrizione',
                10,
                'Corpo del form 1',
                'ABCD',
                '2024-01-01 09:00:00',
                '2024-01-10 18:00:00',
                CURRENT_TIMESTAMP,
                CURRENT_TIMESTAMP,
                NULL,
                '2024-02-01 09:00:00',
                '2024-03-01 09:00:00'
            ),
            (
                accountId_admin,
                'Modulo di feedback',
                'Note sul modulo di feedback',
                5,
                'Corpo del form 2',
                'EFGH',
                '2024-02-01 09:00:00',
                '2024-02-15 17:00:00',
                CURRENT_TIMESTAMP,
                CURRENT_TIMESTAMP,
                NULL,
                '2024-03-01 09:00:00',
                '2024-04-01 09:00:00'
            ),
            (
                accountId_admin,
                'Modulo avanzato',
                'Note sul modulo avanzato',
                20,
                'Corpo del form 3',
                'WXYZ',
                '2024-03-01 00:00:00',
                '2024-03-15 23:59:59',
                CURRENT_TIMESTAMP,
                CURRENT_TIMESTAMP,
                NULL,
                '2024-05-01 00:00:00',
                '2024-06-01 00:00:00'
            )
        ON CONFLICT (url_code) DO NOTHING;


        -- 2.4) Fetch form IDs into variables
        SELECT application_form_id
        INTO formIdOne
        FROM candido.application_form
        WHERE url_code = 'ABCD';

        SELECT application_form_id
        INTO formIdTwo
        FROM candido.application_form
        WHERE url_code = 'EFGH';

        SELECT application_form_id
        INTO formIdThree
        FROM candido.application_form
        WHERE url_code = 'WXYZ';


        -- 2.5) Insert into application
        INSERT INTO candido.application (
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
        )
        VALUES
            (
                1,
                accountId_client,
                formIdOne,
                1,
                '{}',
                50,
                '2023-05-13 10:15:00',
                '2023-05-14 11:20:00',
                NULL,
                NULL
            ),
            (
                2,
                accountId_client,
                formIdTwo,
                2,
                '{}',
                100,
                '2023-09-22 09:30:00',
                '2024-01-10 14:45:00',
                NULL,
                '2024-01-10 14:45:00'
            ),
            (
                3,
                accountId_client,
                formIdThree,
                3,
                '{}',
                100,
                '2024-03-01 09:00:00',
                '2024-03-02 10:00:00',
                NULL,
                '2024-03-02 10:00:00'
            ),
            (
                4,
                accountId_client,
                formIdOne,
                4,
                '{}',
                100,
                '2024-07-15 08:00:00',
                '2024-07-15 15:00:00',
                NULL,
                '2024-07-15 15:00:00'
            ),
            (
                5,
                accountId_client,
                formIdTwo,
                5,
                '{}',
                98,
                '2025-01-28 07:00:00',
                '2025-01-29 08:00:00',
                NULL,
                NULL
            )
        ON CONFLICT (application_id) DO NOTHING;


        -- 2.6) Insert into xref_account_application_saved
        INSERT INTO candido.xref_account_application_saved (
            fk_account_id,
            fk_application_form_id,
            saved_at
        )
        VALUES
            (accountId_client, formIdOne,   '2023-05-13 10:15:00'),
            (accountId_client, formIdTwo,   '2023-09-22 09:30:00'),
            (accountId_client, formIdThree, '2024-03-01 09:00:00')
        ON CONFLICT DO NOTHING;

    END;
$$ LANGUAGE plpgsql;
