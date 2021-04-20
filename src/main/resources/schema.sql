CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS workspace
(
    id uuid DEFAULT uuid_generate_v4(),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS workspace_user
(
    id uuid DEFAULT uuid_generate_v4(),
    nickname varchar NOT NULL,
    roles varchar[],
    user_id uuid NOT NULL,
    workspace_id uuid NOT NULL,
    FOREIGN KEY (workspace_id) REFERENCES workspace(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS task
(
    id uuid DEFAULT uuid_generate_v4(),
    workspace_user_id uuid NOT NULL,
    workspace_id uuid NOT NULL,
    FOREIGN KEY (workspace_user_id) REFERENCES workspace_user(id),
    FOREIGN KEY (workspace_id) REFERENCES workspace(id),
    PRIMARY KEY (id)
);