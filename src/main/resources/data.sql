INSERT IGNORE INTO account(account_id, fk_account_role_id, fk_account_status_id, email, password, created_at, deleted_at)
VALUES
    (1, 2, 2, "admin@candidocorp.com", "", now(), null);

INSERT IGNORE INTO user
    (user_id, fk_account_id, fk_gender_id, fk_address_id, first_name, last_name, birth_date, mobile_number, phone_number, last_modified_name, created_at, deleted_at)
VALUES
    (1, 1, 1, null, "John", "Doe", "1998-04-15", "3481138394", "3458483720", null, now(), null);