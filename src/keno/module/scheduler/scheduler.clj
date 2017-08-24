(ns keno.module.scheduler.scheduler
  (:require [immutant.scheduling :as scheduling]
            [keno.module.db.database :as database]
            [keno.module.engine.engine :as engine]
            ))

(def can-deal-bets (atom false))
(def timer (atom 120))

(defn change-game-bets-confirm [state] (reset! can-deal-bets state))

(defn filter-empty-player-id [sq] (filter #(not (nil? (:player_id %))) sq))

(defn db-bets->bets [db-bets] (map (fn [sq] {
                                             :player_id (:player_id (first sq))
                                             :cost      (:cost (first sq))
                                             :bets_uuid (:bets_uuid (first sq))
                                             :number    (reduce (fn [acc el] (conj acc (:ball_number el))) [] sq)
                                             }) db-bets))

(defn db-winner-bets->bets [winner-db-bets] (vec (map #(:ball_number %) winner-db-bets)))

(defn change-state [] (do
                            ;;inverse state
                            (change-game-bets-confirm (not @can-deal-bets))

                            ;; generate and save balls
                            (let [[round_id] (database/get-round)]
                              (doseq [ball (engine/winner-number)]
                                (database/save-round-balls! (merge {:ball_number ball} round_id))))

                            ;; increase round
                            (database/new-round)

                            ;; calculate user bets and new amount
                            (let [bets (database/get-users-bets-rd)
                                  winner-bets (db-winner-bets->bets (database/winner-balls))
                                  prep (filter-empty-player-id (db-bets->bets (first (group-by :bets_uuid bets))))]
                              (let [calc_res (map
                               #({
                                  :player_id (:player_id %)
                                  :bets_uuid (:bets_uuid %)
                                  :new_cost (engine/calculate-bet (:cost %) (:number %) winner-bets)
                                  })
                               prep)]
                                (map #(do
                                        (database/update-user-bet! {
                                                                    :winner_cost (:new_cost %)
                                                                    :bets_uuid (:bets_uuid %)
                                                                    })
                                        ;; todo remove cost when get bet
                                        (database/update-user-bet! {
                                                                   :winner_cost (+ (:new_cost %) (first (database/get-user-amount {:player_id (:player_id %)})))
                                                                   :bets_uuid (:bets_uuid %)}) 
                                        ) calc_res)
                                )
                            )))

(defn timer-job [] (if (> @timer 0)
                     (swap! timer - 1)
                     (do
                       (reset! timer 120)
                       (change-state))))


(scheduling/schedule timer-job (scheduling/every 1 :second))
