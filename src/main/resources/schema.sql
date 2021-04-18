CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS user_account
(
    id uuid DEFAULT uuid_generate_v4 (),
    external_id uuid NOT NULL,
    username varchar (255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id, external_id)
);