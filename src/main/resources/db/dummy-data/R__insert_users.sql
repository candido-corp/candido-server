DO $$
DECLARE
accountId_admin   bigint;
    accountId_client  bigint;
BEGIN
    -- Fetch account IDs
SELECT account_id INTO accountId_admin FROM candido.account WHERE email = 'admin@candidocorp.com';
SELECT account_id INTO accountId_client FROM candido.account WHERE email = 'client@candidocorp.com';

-- Insert users
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
    (accountId_admin, 1, NULL, 'Admin', 'Doe', '1998-04-15', '12345678', '', NULL, now(), NULL),
    (accountId_client, 1, NULL, 'Client', 'Ino', '1975-10-22', '999999999', '3458483720', NULL, now(), NULL)
    ON CONFLICT DO NOTHING;
END;
$$ LANGUAGE plpgsql;
