TRUNCATE TABLE reviews;

ALTER TABLE reviews ALTER COLUMN rating TYPE jsonb USING '[]'::jsonb;
