
DROP TABLE IF EXISTS activity CASCADE;
DROP TABLE IF EXISTS user_db CASCADE;
DROP SEQUENCE IF EXISTS user_db_id_seq CASCADE;
DROP SEQUENCE IF EXISTS activity_id_seq CASCADE;

CREATE SEQUENCE user_db_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE user_db (
    id BIGINT PRIMARY KEY DEFAULT nextval('user_db_id_seq'),
    name VARCHAR(255) UNIQUE NOT NULL,
    encoded_password VARCHAR(60) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT user_role_check CHECK (role IN ('USER', 'ADMIN'))
);

CREATE SEQUENCE activity_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE activity (
    id BIGINT PRIMARY KEY DEFAULT nextval('activity_id_seq'),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    is_template BOOLEAN NOT NULL DEFAULT FALSE,
    user_id BIGINT,
    category VARCHAR(50) NOT NULL,
    frequency VARCHAR(50) NOT NULL,
    custom_frequency_value INTEGER,
    custom_frequency_unit VARCHAR(20),
    status VARCHAR(50) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    estimated_duration INTEGER,
    due_moment TIMESTAMP,
    completed_at TIMESTAMP,

    CONSTRAINT fk_activity_user FOREIGN KEY (user_id) REFERENCES user_db(id) ON DELETE CASCADE,

    CONSTRAINT activity_category_check CHECK (category IN ('WISDOM', 'WELLBEING', 'ENERGY', 'SELF_CARE', 'OTHER')),
    CONSTRAINT activity_frequency_check CHECK (frequency IN ('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY', 'CUSTOM')),
    CONSTRAINT activity_frequency_unit_check CHECK (custom_frequency_unit IS NULL OR custom_frequency_unit IN ('MINUTES', 'HOURS', 'DAYS', 'WEEKS', 'MONTHS')),
    CONSTRAINT activity_status_check CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),
    CONSTRAINT activity_priority_check CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL'))
);

CREATE INDEX idx_activity_user_id ON activity(user_id);
CREATE INDEX idx_activity_is_template ON activity(is_template);
CREATE INDEX idx_activity_category ON activity(category);
CREATE INDEX idx_activity_status ON activity(status);
CREATE INDEX idx_activity_created_at ON activity(created_at);

SELECT setval('user_db_id_seq', (SELECT MAX(id) FROM user_db));
SELECT setval('activity_id_seq', 1);
