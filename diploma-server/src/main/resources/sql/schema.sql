-- START >>> table 'authorities'
CREATE TABLE IF NOT EXISTS authorities
(
    id            BIGSERIAL        NOT NULL PRIMARY KEY,
    name          VARCHAR(100)     NOT NULL UNIQUE
)
WITH
(
    OIDS = FALSE
);
ALTER TABLE authorities OWNER to postgres;
-- END <<< table 'authorities'

-- START >>> table 'security_profiles'
CREATE TABLE IF NOT EXISTS security_profiles
(
    id         BIGSERIAL     NOT NULL PRIMARY KEY,
    name       VARCHAR(50)   NOT NULL UNIQUE,
    created    TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated    TIMESTAMP     NULL,
    deleted    BOOLEAN       NOT NULL DEFAULT FALSE
)
WITH
(
    OIDS = FALSE
);
ALTER TABLE security_profiles OWNER to postgres;
-- END <<< table 'security_profiles'

-- START >>> table 'security_profile_authorities'
CREATE TABLE IF NOT EXISTS security_profile_authorities
(
    profile_id      BIGINT    NOT NULL,
    authority_id    BIGINT    NOT NULL,
    CONSTRAINT security_profile_authorities_pk PRIMARY KEY (profile_id, authority_id),
    CONSTRAINT security_profiles_fk FOREIGN KEY (profile_id) REFERENCES security_profiles (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT authorities_fk FOREIGN KEY (authority_id) REFERENCES authorities (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH
(
    OIDS = FALSE
);
ALTER TABLE security_profile_authorities OWNER to postgres;
-- END <<< table 'security_profile_authorities'

-- START >>> table 'accounts'
CREATE TABLE IF NOT EXISTS accounts
(
    id            BIGSERIAL        NOT NULL PRIMARY KEY,
    username      VARCHAR(30)      NOT NULL UNIQUE,
    password      VARCHAR(1024)    NOT NULL,
    email         VARCHAR(128)     NULL UNIQUE,
    first_name    VARCHAR(60)      NOT NULL,
    last_name     VARCHAR(60)      NOT NULL,
    profile_id    BIGINT           NULL,
    created       TIMESTAMP        NOT NULL DEFAULT NOW(),
    updated       TIMESTAMP        NULL,
    deleted       BOOLEAN          NOT NULL DEFAULT FALSE,
    CONSTRAINT security_profiles_fk FOREIGN KEY (profile_id) REFERENCES security_profiles (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
)
WITH
(
    OIDS = FALSE
);
ALTER TABLE accounts OWNER to postgres;
-- END <<< table 'accounts'

-- START >>> table 'character_sheets'
CREATE TABLE IF NOT EXISTS character_sheets
(
    id     BIGSERIAL    NOT NULL PRIMARY KEY,
    str    SMALLINT     NOT NULL,
    dex    SMALLINT     NOT NULL,
    con    SMALLINT     NOT NULL,
    int    SMALLINT     NOT NULL,
    wis    SMALLINT     NOT NULL,
    cha    SMALLINT     NOT NULL
    -- FIXME: add all required fields!!!
)
WITH
(
    OIDS = FALSE
);
ALTER TABLE character_sheets OWNER to postgres;
-- END <<< table 'character_sheets'

-- START >>> table 'character_classes'
CREATE TABLE IF NOT EXISTS character_classes
(
    id                    BIGSERIAL      NOT NULL PRIMARY KEY,
    class_name            VARCHAR(30)    NOT NULL UNIQUE,
    class_level           SMALLINT       NOT NULL,
    character_sheet_id    BIGINT         NOT NULL,

    CONSTRAINT character_classes_fk FOREIGN KEY (character_sheet_id) REFERENCES character_sheets (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
)
WITH
(
    OIDS = FALSE
);
ALTER TABLE character_classes OWNER to postgres;
-- END <<< table 'character_classes'