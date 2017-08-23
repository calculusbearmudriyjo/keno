CREATE TABLE game (
	round_id int auto_increment primary key
);

INSERT INTO game VALUES (1);

CREATE TABLE balls (
	id int auto_increment primary key,
	round_id int not null,
	ball_number int(2)
);

CREATE TABLE bets (
	bets_uuid char(32) not null primary key,
	round_id int not null,
	client_uuid char(32) not null,
	date_start timestamp not null default CURRENT_TIMESTAMP,
	date_calc timestamp not null default CURRENT_TIMESTAMP,
	status int(1) not null,
	app_uuid char(32) not null,
	cost decimal(12,2),
	winner_cost decimal(12,2)
);