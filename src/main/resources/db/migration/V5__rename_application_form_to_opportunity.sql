ALTER TABLE application_form RENAME TO opportunity;
ALTER TABLE xref_account_application_saved RENAME TO xref_account_opportunity_saved;
ALTER TABLE xref_account_opportunity_saved RENAME COLUMN fk_application_form_id TO fk_opportunity_id;

ALTER TABLE opportunity RENAME COLUMN application_form_id TO opportunity_id;
ALTER TABLE opportunity RENAME COLUMN application_form_body TO form_schema;

ALTER TABLE application RENAME COLUMN fk_application_form_id TO fk_opportunity_id;
ALTER TABLE application_form_admin RENAME COLUMN fk_application_form_id TO fk_opportunity_id;

ALTER TABLE opportunity ALTER COLUMN form_schema TYPE JSONB USING form_schema::jsonb;
