DO $$
DECLARE
accountId_admin bigint;
BEGIN
SELECT account_id INTO accountId_admin FROM candido.account WHERE email = 'admin@candidocorp.com';

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
    (accountId_admin, 'Modulo di iscrizione', 'Note iniziali del modulo di iscrizione', 10, 'Corpo del form 1', 'ABCD', '2024-01-01 09:00:00', '2024-01-10 18:00:00', current_timestamp, current_timestamp, NULL, '2024-02-01 09:00:00', '2024-03-01 09:00:00'),
    (accountId_admin, 'Modulo di feedback', 'Note sul modulo di feedback', 5, 'Corpo del form 2', 'EFGH', '2024-02-01 09:00:00', '2024-02-15 17:00:00', current_timestamp, current_timestamp, NULL, '2024-03-01 09:00:00', '2024-04-01 09:00:00'),
    (accountId_admin, 'Modulo avanzato', 'Note sul modulo avanzato', 20, 'Corpo del form 3', 'WXYZ', '2024-03-01 00:00:00', '2024-03-15 23:59:59', current_timestamp, current_timestamp, NULL, '2024-05-01 00:00:00', '2024-06-01 00:00:00')
    ON CONFLICT (url_code) DO NOTHING;
END;
$$ LANGUAGE plpgsql;
