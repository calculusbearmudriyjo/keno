CREATE TABLE game (
	round_id int auto_increment primary key,
	CONSTRAINT game UNIQUE (round_id)
);

INSERT INTO game VALUES (1);

CREATE TABLE balls (
	id int auto_increment primary key,
	round_id int not null,
	ball_number int(2)
);

CREATE TABLE bets (
	bets_uuid char(36) not null primary key,
	player_id int,
	round_id int not null,
	date_start timestamp default CURRENT_TIMESTAMP,
	date_calc timestamp default CURRENT_TIMESTAMP,
	status int(1) not null,
	app_uuid char(36) not null,
	cost decimal(12,2) not null,
	winner_cost decimal(12,2),
	CONSTRAINT bets UNIQUE (bets_uuid,round_id)
);

CREATE TABLE player_balls (
	id int auto_increment primary key,
	bets_uuid char(36) not null,
	ball_number int(2) not null
);

CREATE TABLE player (
	id int auto_increment primary key,
	name char(255) not null,
	client_uuid char(36) not null,
	amount decimal(12,2),
	CONSTRAINT player UNIQUE (client_uuid)
);