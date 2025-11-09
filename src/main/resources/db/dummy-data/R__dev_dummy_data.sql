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


DO $$
    DECLARE
        account_id_admin BIGINT;
        currency_id_eur BIGINT;
        opportunity_type_job BIGINT;
        opportunity_status_published BIGINT;
        location_type_remote BIGINT;
        opportunity_level_entry BIGINT;
    BEGIN
        -- Account admin
        SELECT account_id INTO account_id_admin FROM candido.account WHERE email = 'admin@candidocorp.com';

        -- Foreign keys
        SELECT currency_id INTO currency_id_eur FROM currency WHERE code = 'EUR';
        SELECT opportunity_type_id INTO opportunity_type_job FROM opportunity_type WHERE key = 'job';
        SELECT opportunity_status_id INTO opportunity_status_published FROM opportunity_status WHERE key = 'published';
        SELECT location_type_id INTO location_type_remote FROM location_type WHERE key = 'remote';
        SELECT opportunity_level_id INTO opportunity_level_entry FROM opportunity_level WHERE key = 'entry';

        -- Dummy data
        INSERT INTO opportunity (
            fk_account_id,
            display_name,
            notes,
            max_applicants,
            form_schema,
            url_code,
            start_date,
            end_date,
            created_at,
            updated_at,
            deleted_at,
            feedback_publication_date,
            feedback_expiration_date,
            description,
            compensation_amount,
            compensation_note,
            fk_currency_id,
            fk_territory_id,
            fk_location_type_id,
            fk_opportunity_type_id,
            fk_opportunity_status_id,
            fk_opportunity_level_id
        ) VALUES
              (account_id_admin, 'Software Engineer Intern', 'Entry internship', 10, '{"fields":[{"label":"CV","type":"file"}]}', 'ABCD', '2024-01-01', '2024-01-31', now(), now(), null, '2024-02-10', '2024-02-20', 'Internship in software development.', 800.00, 'Monthly stipend', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry),
              (account_id_admin, 'Backend Developer', 'Looking for backend devs', 5, '{"fields":[{"label":"GitHub","type":"text"}]}', 'EFGH', '2024-02-01', '2024-02-15', now(), now(), null, '2024-02-20', '2024-03-01', 'Develop backend APIs in Java.', 3000.00, 'Full-time compensation', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry),
              (account_id_admin, 'Frontend Developer', 'Vue/React needed', 7, '{"fields":[{"label":"Portfolio","type":"url"}]}', 'IJKL', '2024-02-05', '2024-02-25', now(), now(), null, '2024-03-01', '2024-03-15', 'Frontend UI/UX engineer role.', 2800.00, 'Salary + bonus', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry),
              (account_id_admin, 'Full Stack Bootcamp', 'Training opportunity', 25, '{"fields":[{"label":"Motivation","type":"text"}]}', 'MNOP', '2024-03-01', '2024-03-20', now(), now(), null, '2024-03-30', '2024-04-15', 'Join our full-stack bootcamp and get hired.', 0.00, 'Free training', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry),
              (account_id_admin, 'DevOps Role', 'Kubernetes + CI/CD', 3, '{"fields":[{"label":"Experience","type":"textarea"}]}', 'QRST', '2024-03-10', '2024-03-25', now(), now(), null, '2024-03-28', '2024-04-10', 'We need a skilled DevOps engineer.', 3500.00, 'Includes relocation', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry),
              (account_id_admin, 'AI Challenge 2024', 'Competition format', 100, '{"fields":[{"label":"Team Name","type":"text"}]}', 'UVWX', '2024-04-01', '2024-04-15', now(), now(), null, '2024-04-30', '2024-05-15', 'Compete in solving real-world AI problems.', 5000.00, 'Prize money', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry),
              (account_id_admin, 'Data Science Intern', 'Data internship', 4, '{"fields":[{"label":"Why Data?","type":"text"}]}', 'YZ12', '2024-05-01', '2024-05-20', now(), now(), null, '2024-05-30', '2024-06-10', 'Analyze datasets, build ML models.', 900.00, 'Monthly stipend', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry),
              (account_id_admin, 'Cybersecurity Role', 'Security analyst', 2, '{"fields":[{"label":"Certifications","type":"text"}]}', '3456', '2024-06-01', '2024-06-15', now(), now(), null, '2024-06-20', '2024-07-01', 'Protect infrastructure and monitor threats.', 4000.00, 'High responsibility', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry),
              (account_id_admin, 'UX/UI Designer', 'Design-focused', 6, '{"fields":[{"label":"Design samples","type":"file"}]}', '7890', '2024-06-10', '2024-06-30', now(), now(), null, '2024-07-05', '2024-07-20', 'Focus on UI/UX improvements.', 2700.00, 'Plus hardware budget', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry),
              (account_id_admin, 'Product Manager', 'Junior PM wanted', 1, '{"fields":[{"label":"PM Experience","type":"textarea"}]}', '1122', '2024-07-01', '2024-07-20', now(), now(), null, '2024-07-25', '2024-08-10', 'Assist in product lifecycle and roadmap.', 3200.00, 'Flexible hours', currency_id_eur, 1, location_type_remote, opportunity_type_job, opportunity_status_published, opportunity_level_entry)
        ON CONFLICT DO NOTHING;
    END;
$$ LANGUAGE plpgsql;


DO $$
    DECLARE
        accountId_client bigint;
        opportunityIdOne bigint;
        opportunityIdTwo bigint;
        opportunityIdThree bigint;
    BEGIN
        SELECT account_id INTO accountId_client FROM candido.account WHERE email = 'client@candidocorp.com';

        SELECT opportunity_id INTO opportunityIdOne FROM candido.opportunity WHERE url_code = 'ABCD';
        SELECT opportunity_id INTO opportunityIdTwo FROM candido.opportunity WHERE url_code = 'EFGH';
        SELECT opportunity_id INTO opportunityIdThree FROM candido.opportunity WHERE url_code = 'IJKL';

        IF opportunityIdOne IS NOT NULL AND opportunityIdTwo IS NOT NULL AND opportunityIdThree IS NOT NULL THEN
            INSERT INTO candido.application (
                application_id,
                fk_account_id,
                fk_opportunity_id,
                fk_application_status_id,
                filled_body,
                progress,
                created_at,
                updated_at,
                deleted_at,
                sent_at
            )
            VALUES
                (1, accountId_client, opportunityIdOne, 1, '{}', 50, '2023-05-13 10:15:00', '2023-05-14 11:20:00', NULL, NULL),
                (2, accountId_client, opportunityIdTwo, 2, '{}', 100, '2023-09-22 09:30:00', '2024-01-10 14:45:00', NULL, '2024-01-10 14:45:00'),
                (3, accountId_client, opportunityIdThree, 3, '{}', 100, '2024-03-01 09:00:00', '2024-03-02 10:00:00', NULL, '2024-03-02 10:00:00'),
                (4, accountId_client, opportunityIdOne, 4, '{}', 100, '2024-07-15 08:00:00', '2024-07-15 15:00:00', NULL, '2024-07-15 15:00:00'),
                (5, accountId_client, opportunityIdTwo, 5, '{}', 98, '2025-01-28 07:00:00', '2025-01-29 08:00:00', NULL, NULL)
            ON CONFLICT (application_id) DO NOTHING;
        END IF;
    END;
$$ LANGUAGE plpgsql;


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
            (accountId_admin, 1, 'Admin', 'Doe', '1998-04-15', '12345678', '', NULL, now(), NULL),
            (accountId_client, 1, 'Client', 'Ino', '1975-10-22', '999999999', '3458483720', NULL, now(), NULL)
        ON CONFLICT DO NOTHING;
    END;
$$ LANGUAGE plpgsql;


DO $$
    DECLARE
        accountId_client bigint;
        opportunityIdOne bigint;
        opportunityIdTwo bigint;
        opportunityIdThree bigint;
    BEGIN
        -- Get account ID
        SELECT account_id INTO accountId_client FROM candido.account WHERE email = 'client@candidocorp.com';

        -- Get opportunity IDs by url_code
        SELECT opportunity_id INTO opportunityIdOne FROM candido.opportunity WHERE url_code = 'ABCD';
        SELECT opportunity_id INTO opportunityIdTwo FROM candido.opportunity WHERE url_code = 'EFGH';
        SELECT opportunity_id INTO opportunityIdThree FROM candido.opportunity WHERE url_code = 'IJKL';

        -- Insert saved opportunities
        INSERT INTO candido.xref_account_opportunity_saved (
            fk_account_id,
            fk_opportunity_id,
            saved_at
        )
        VALUES
            (accountId_client, opportunityIdOne, '2023-05-13 10:15:00'),
            (accountId_client, opportunityIdTwo, '2023-09-22 09:30:00'),
            (accountId_client, opportunityIdThree, '2024-03-01 09:00:00')
        ON CONFLICT DO NOTHING;
    END;
$$ LANGUAGE plpgsql;

