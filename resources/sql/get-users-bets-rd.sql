SELECT * FROM bets LEFT JOIN player_balls ON (bets.bets_uuid = player_balls.bets_uuid) WHERE round_id = (SELECT max(round_id) FROM game);
