CREATE SEQUENCE tlink_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE tsearch_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE ttag_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE tuser_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE TABLE tlink (
    id integer DEFAULT nextval('public.tlink_id_seq') NOT NULL,
    address character varying(1024) NOT NULL,
    name character varying(1024),
    description text,
    ldate timestamp without time zone,
    date_mod timestamp without time zone,
    clicks integer DEFAULT 0,
    group_id smallint,
    shortcut character varying(20),
    icon bytea,
    user_id smallint,
    click_date timestamp without time zone,
    pub boolean DEFAULT true
);

CREATE TABLE tlinktag (
    link_id bigint NOT NULL,
    tag_id bigint NOT NULL
);

CREATE TABLE tsearch (
    id integer DEFAULT nextval('public.tsearch_id_seq') NOT NULL,
    phrase character varying(255),
    counter integer DEFAULT 1,
    date timestamp without time zone
);

CREATE TABLE ttag (
    id integer DEFAULT nextval('public.ttag_id_seq') NOT NULL,
    tag character varying(256)
);

CREATE TABLE tuser (
    id smallint DEFAULT nextval('public.tuser_id_seq') NOT NULL,
    "login" character varying(50) NOT NULL,
    pass character varying(40) NOT NULL,
    "role" smallint DEFAULT 0 NOT NULL,
    "mode" smallint DEFAULT 0 NOT NULL,
    "join_date" timestamp without time zone
);
