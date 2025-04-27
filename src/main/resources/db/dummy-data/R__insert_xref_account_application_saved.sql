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

INSERT INTO candido.xref_account_application_saved (
    fk_account_id,
    fk_application_form_id,
    saved_at
)
VALUES
    (accountId_client, formIdOne, '2023-05-13 10:15:00'),
    (accountId_client, formIdTwo, '2023-09-22 09:30:00'),
    (accountId_client, formIdThree, '2024-03-01 09:00:00')
    ON CONFLICT DO NOTHING;
END;
$$ LANGUAGE plpgsql;
