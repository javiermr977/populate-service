# populate-service
Api encargada de obtener la info desde marvel y poblar la base de datos local a demanda



CREATE DATABASE marvel_db;

-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION postgres;


-- public.audit definition

-- Drop table

-- DROP TABLE public.audit;

CREATE TABLE public.audit (
	id bigserial NOT NULL,
	"action" varchar(255) NULL,
	created_at timestamp NULL,
	user_id int8 NULL,
	CONSTRAINT audit_pkey PRIMARY KEY (id)
);


-- public.audit foreign keys

ALTER TABLE public.audit ADD CONSTRAINT fkn2jr87wh10l0fmmox82athugs FOREIGN KEY (user_id) REFERENCES public.users(id);


-- public.character_comics definition

-- Drop table

-- DROP TABLE public.character_comics;

CREATE TABLE public.character_comics (
	id bigserial NOT NULL,
	character_id int8 NULL,
	comic_id int8 NULL,
	CONSTRAINT character_comics_pkey PRIMARY KEY (id)
);


-- public.character_comics foreign keys

ALTER TABLE public.character_comics ADD CONSTRAINT fki6t4i7fgos99mcvultfu7vgof FOREIGN KEY (comic_id) REFERENCES public.comics(id);
ALTER TABLE public.character_comics ADD CONSTRAINT fkphjddndvnx0evft332t9cku6b FOREIGN KEY (character_id) REFERENCES public."characters"(id);

-- public.character_comics definition

-- Drop table

-- DROP TABLE public.character_comics;

CREATE TABLE public.character_comics (
	id bigserial NOT NULL,
	character_id int8 NULL,
	comic_id int8 NULL,
	CONSTRAINT character_comics_pkey PRIMARY KEY (id)
);


-- public.character_comics foreign keys

ALTER TABLE public.character_comics ADD CONSTRAINT fki6t4i7fgos99mcvultfu7vgof FOREIGN KEY (comic_id) REFERENCES public.comics(id);
ALTER TABLE public.character_comics ADD CONSTRAINT fkphjddndvnx0evft332t9cku6b FOREIGN KEY (character_id) REFERENCES public."characters"(id);

-- public.character_comics definition

-- Drop table

-- DROP TABLE public.character_comics;

CREATE TABLE public.character_comics (
	id bigserial NOT NULL,
	character_id int8 NULL,
	comic_id int8 NULL,
	CONSTRAINT character_comics_pkey PRIMARY KEY (id)
);


-- public.character_comics foreign keys

ALTER TABLE public.character_comics ADD CONSTRAINT fki6t4i7fgos99mcvultfu7vgof FOREIGN KEY (comic_id) REFERENCES public.comics(id);
ALTER TABLE public.character_comics ADD CONSTRAINT fkphjddndvnx0evft332t9cku6b FOREIGN KEY (character_id) REFERENCES public."characters"(id);

-- public."characters" definition

-- Drop table

-- DROP TABLE public."characters";

CREATE TABLE public."characters" (
	id bigserial NOT NULL,
	character_code int8 NULL,
	description text NULL,
	image varchar(255) NULL,
	"name" varchar(255) NULL,
	image_ext varchar(255) NULL,
	CONSTRAINT characters_pkey PRIMARY KEY (id),
	CONSTRAINT uk_n8297dpwmna4yra350yeit8id UNIQUE (character_code)
);


-- public.comics definition

-- Drop table

-- DROP TABLE public.comics;

CREATE TABLE public.comics (
	id bigserial NOT NULL,
	comic_code int8 NULL,
	"name" varchar(255) NULL,
	CONSTRAINT comics_pkey PRIMARY KEY (id),
	CONSTRAINT uk_gbt5294rldtgivxs8ge2ej5y6 UNIQUE (comic_code)
);

-- public.creator_stories definition

-- Drop table

-- DROP TABLE public.creator_stories;

CREATE TABLE public.creator_stories (
	id bigserial NOT NULL,
	creator_id int8 NULL,
	story_id int8 NULL,
	CONSTRAINT creator_stories_pkey PRIMARY KEY (id)
);


-- public.creator_stories foreign keys

ALTER TABLE public.creator_stories ADD CONSTRAINT fkbxlyra6j1gxvn23jabbgw4y4x FOREIGN KEY (creator_id) REFERENCES public.creators(id);
ALTER TABLE public.creator_stories ADD CONSTRAINT fkcn221slkydlg9h23sm9bfk0o6 FOREIGN KEY (story_id) REFERENCES public.stories(id);


-- public.creators definition

-- Drop table

-- DROP TABLE public.creators;

CREATE TABLE public.creators (
	id bigserial NOT NULL,
	creator_code int8 NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	middle_name varchar(255) NULL,
	CONSTRAINT creators_pkey PRIMARY KEY (id)
);


-- public.series definition

-- Drop table

-- DROP TABLE public.series;

CREATE TABLE public.series (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	serie_code int8 NULL,
	CONSTRAINT series_pkey PRIMARY KEY (id),
	CONSTRAINT uk_4d53n0mkjw7fmyi6oh5coy07b UNIQUE (serie_code)
);

-- public.stories definition

-- Drop table

-- DROP TABLE public.stories;

CREATE TABLE public.stories (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	story_code int8 NULL,
	type_id int8 NULL,
	CONSTRAINT stories_pkey PRIMARY KEY (id),
	CONSTRAINT uk_gw4vn84l9787tsu8xlfj9ajnv UNIQUE (story_code)
);


-- public.stories foreign keys

ALTER TABLE public.stories ADD CONSTRAINT fk5c088pauad91hqrk64bgp56w1 FOREIGN KEY (type_id) REFERENCES public.story_types(id);


-- public.story_types definition

-- Drop table

-- DROP TABLE public.story_types;

CREATE TABLE public.story_types (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT story_types_pkey PRIMARY KEY (id),
	CONSTRAINT uk_he7te93ys11eg2u5qckya4rxf UNIQUE (name)
);

-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id bigserial NOT NULL,
	key_user varchar(255) NULL,
	"password" varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);