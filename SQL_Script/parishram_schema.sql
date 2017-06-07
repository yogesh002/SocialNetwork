CREATE TABLE IF NOT EXISTS login(
	login_id SERIAL NOT NULL UNIQUE,
	username VARCHAR(55) NOT NULL UNIQUE,
	password VARCHAR(55) NOT NULL,
	email VARCHAR(55) NOT NULL UNIQUE CHECK (email LIKE '%@%.com'),
	enabled SMALLINT NOT NULL DEFAULT 0,
CONSTRAINT pk_login_person_id PRIMARY KEY (login_id)
);

CREATE TABLE IF NOT EXISTS school(
	login_id SMALLINT,
	school_name VARCHAR(55),
	school_city VARCHAR(55),
	school_state VARCHAR(55),
	school_street VARCHAR(100),
	field_of_study VARCHAR(55),
	level_of_education VARCHAR(55),
CONSTRAINT fk_school_login_id FOREIGN KEY(login_id) REFERENCES login(login_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS address(
	login_id SMALLINT,
	city VARCHAR(55),
	state VARCHAR(55),
	zip CHAR(5),
	country VARCHAR(55),
CONSTRAINT fk_address_login_id FOREIGN KEY(login_id) REFERENCES login(login_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS role(
	login_id SMALLINT,
	role VARCHAR(55) NOT NULL DEFAULT 'ROLE_USER',
CONSTRAINT fk_role_login_id FOREIGN KEY(login_id) REFERENCES login(login_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS religion_description(
	religion_id SERIAL PRIMARY KEY,
	religion VARCHAR(55)
);

CREATE TABLE IF NOT EXISTS user_religion(
	login_id SMALLINT,
	religion_id SMALLINT,
CONSTRAINT fk_user_religion_religion_id FOREIGN KEY(religion_id) REFERENCES religion_description(religion_id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_user_religion_login_id FOREIGN KEY (login_id) REFERENCES login(login_id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT uq_user_religion_login_id UNIQUE(login_id) 
);

CREATE TABLE IF NOT EXISTS persondetails(
	persondetails_id SERIAL PRIMARY KEY,
	login_id SMALLINT UNIQUE NOT NULL,
	firstname VARCHAR(55),
	lastname VARCHAR(55),
	age SMALLINT,
	weight NUMERIC(5, 2),
	occupation VARCHAR(55),
	gender CHAR(5),
	salary DECIMAL,
CONSTRAINT fk_personaldetails_login_id FOREIGN KEY(login_id) REFERENCES login(login_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS comments(
	comment_id SERIAL PRIMARY KEY,
	from_user SMALLINT,
	to_user SMALLINT,
	comments VARCHAR(200),
	image_id SMALLINT,
	status_id SMALLINT,
CONSTRAINT fk_comments_login_id FOREIGN KEY (to_user) REFERENCES login(login_id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_image_id FOREIGN KEY (image_id) REFERENCES image(image_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS IMAGE
(
  image_id SERIAL PRIMARY KEY,
  image TEXT,
  is_profile_pic BOOLEAN DEFAULT false,
  login_id SMALLINT,
  CONSTRAINT fk_image_login_id FOREIGN KEY (login_id) REFERENCES public.login (login_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS status
(
  status_id SERIAL PRIMARY KEY,
  from_user SMALLINT,
  to_user SMALLINT,
  status TEXT,
  time_created timestamp DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_status_login_id FOREIGN KEY (to_user) REFERENCES public.login (login_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS likes(
	like_id SERIAL PRIMARY KEY,
	from_user SMALLINT,
	to_user SMALLINT,
	image_id SMALLINT DEFAULT -1,
	status_id SMALLINT DEFAULT -1,
	comment_id SMALLINT DEFAULT -1,
	CONSTRAINT fk_likes_image_id FOREIGN KEY(image_id) REFERENCES image(image_id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_likes_status_id FOREIGN KEY(status_id) REFERENCES status(status_id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_likes_comment_id FOREIGN KEY(comment_id) REFERENCES comments(comment_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friendrequest(
	from_user SMALLINT,
	to_user SMALLINT,
	accept SMALLINT DEFAULT -1

);

CREATE TABLE IF NOT EXISTS mail(
	email_id SERIAL PRIMARY KEY,
	from_user VARCHAR(50) CHECK (from_user LIKE '%@%.%'),
	to_user VARCHAR (50) DEFAULT 'yogeshghimire002@gmail.com',
	subject VARCHAR(50),
	message VARCHAR(200),
	read SMALLINT DEFAULT 0,
	sent_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS mail_reply(	
	email_id SMALLINT NOT NULL,
	from_user VARCHAR(50) NOT NULL,
	to_user VARCHAR(50) NOT NULL,
	reply_msg VARCHAR(200),
	reply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE IF NOT EXISTS album (
	album_id SERIAL PRIMARY KEY,
	album_name VARCHAR(50),
	login_id SMALLINT NOT NULL,
	rating SMALLINT DEFAULT 0,
CONSTRAINT fk_album_login_id FOREIGN KEY(login_id) REFERENCES login(login_id) ON DELETE CASCADE ON UPDATE CASCADE
);

alter table login add column phone BIGINT
