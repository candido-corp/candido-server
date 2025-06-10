-- Add new column for symbolic key
ALTER TABLE candido.address_type ADD COLUMN address_type_key VARCHAR(50);

-- Update descriptions and keys
UPDATE candido.address_type
SET description = 'Residential',
    address_type_key = 'ADDR_TYPE_RESIDENTIAL'
WHERE address_type_id = 1;

UPDATE candido.address_type
SET description = 'Company',
    address_type_key = 'ADDR_TYPE_COMPANY'
WHERE address_type_id = 2;

UPDATE candido.address_type
SET description = 'Postal',
    address_type_key = 'ADDR_TYPE_POSTAL'
WHERE address_type_id = 3;

UPDATE candido.address_type
SET description = 'Other',
    address_type_key = 'ADDR_TYPE_OTHER'
WHERE address_type_id = 4;

ALTER TABLE candido.address_type ALTER COLUMN address_type_key SET NOT NULL;
ALTER TABLE candido.address_type ADD CONSTRAINT uq_address_type_key UNIQUE (address_type_key);
