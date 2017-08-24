SELECT * FROM balls WHERE round_id = (SELECT max(round_id) FROM game);
