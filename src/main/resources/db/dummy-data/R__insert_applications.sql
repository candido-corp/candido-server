DO $$
DECLARE
accountId_client bigint;
    formIdOne bigint;
    formIdTwo bigint;
    formIdThree bigint;
BEGIN
SELECT account_id INTO accountId_client FROM candido.account WHERE email = 'client@candidocorp.com';
SELECT application_form_id INTO formIdOne FROM candido.application_form WHERE url_code = 'ABCD';
SELECT application_form_id INTO formIdTwo FROM candido.application_form WHERE url_code = 'EFGH';
SELECT application_form_id INTO formIdThree FROM candido.application_form WHERE url_code = 'WXYZ';

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
    (1, accountId_client, formIdOne, 1, '{}', 50, '2023-05-13 10:15:00', '2023-05-14 11:20:00', NULL, NULL),
    (2, accountId_client, formIdTwo, 2, '{}', 100, '2023-09-22 09:30:00', '2024-01-10 14:45:00', NULL, '2024-01-10 14:45:00'),
    (3, accountId_client, formIdThree, 3, '{}', 100, '2024-03-01 09:00:00', '2024-03-02 10:00:00', NULL, '2024-03-02 10:00:00'),
    (4, accountId_client, formIdOne, 4, '{}', 100, '2024-07-15 08:00:00', '2024-07-15 15:00:00', NULL, '2024-07-15 15:00:00'),
    (5, accountId_client, formIdTwo, 5, '{}', 98, '2025-01-28 07:00:00', '2025-01-29 08:00:00', NULL, NULL)
    ON CONFLICT (application_id) DO NOTHING;
END;
$$ LANGUAGE plpgsql;
