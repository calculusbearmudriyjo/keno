SELECT * FROM bets LEFT JOIN player_balls ON (bets.bets_uuid = player_balls.bets_uuid) WHERE player_id = :player_id;
