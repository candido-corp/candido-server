-- Insert dummy accounts
INSERT INTO candido.account (
    fk_account_role_id,
    fk_account_status_id,
    email,
    password,
    created_at,
    deleted_at
)
VALUES
    (2, 2, 'admin@candidocorp.com', '', now(), null),
    (2, 2, 'client@candidocorp.com', '', now(), null)
    ON CONFLICT (email) DO NOTHING;
