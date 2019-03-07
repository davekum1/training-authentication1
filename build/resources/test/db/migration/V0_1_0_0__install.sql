CREATE TABLE auth_user (
    uuid UUID PRIMARY KEY NOT NULL,
    login TEXT UNIQUE,
    email TEXT UNIQUE,
    first_name TEXT,
    last_name TEXT,
    password TEXT,
    date_created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);