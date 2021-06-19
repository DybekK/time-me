CREATE SCHEMA IF NOT EXISTS public;
SET SCHEMA 'public';
/* [jooq ignore start] */
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
/* [jooq ignore stop] */

CREATE TABLE IF NOT EXISTS workspace
(
    id uuid
        /* [jooq ignore start] */ DEFAULT uuid_generate_v4() /* [jooq ignore stop] */,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS workspace_user
(
    id uuid
        /* [jooq ignore start] */ DEFAULT uuid_generate_v4() /* [jooq ignore stop] */,
    nickname varchar,
    roles varchar[] DEFAULT array[]::varchar[],
    user_id uuid NOT NULL,
    workspace_id uuid NOT NULL,
    FOREIGN KEY (workspace_id) REFERENCES workspace(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS task
(
    id uuid
        /* [jooq ignore start] */ DEFAULT uuid_generate_v4() /* [jooq ignore stop] */,
    workspace_user_id uuid NOT NULL,
    workspace_id uuid NOT NULL,
    title varchar NOT NULL,
    FOREIGN KEY (workspace_user_id) REFERENCES workspace_user(id),
    FOREIGN KEY (workspace_id) REFERENCES workspace(id),
    PRIMARY KEY (id)
);