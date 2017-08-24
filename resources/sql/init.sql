SELECT * FROM game as g LEFT JOIN balls as b ON (g.round_id = b.round_id) WHERE g.round_id = (SELECT max(round_id) FROM game) and ball_number is not null;
