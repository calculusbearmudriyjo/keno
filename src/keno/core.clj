(ns keno.core
  (:gen-class)
  (:require [ring.middleware.json :as json]
            [compojure.handler :as handler]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [immutant.web :as immutant]
            [immutant.web.middleware :as mw]
            [keno.module.db.database :as database]
            [keno.module.scheduler.scheduler :as scheduler]))

(defroutes app-routes
  (context "/player" []  
           (POST "/"
                 request 
                 (let [name        (get-in request [:body "name"])
                       client_uuid (get-in request [:body "client_uuid"])
                       system-type (get-in request [:body "system-type"])
                       amount      (get-in request [:body "amount"])]
                   (do
                     (database/set-player! {:nm name :client_uuid client_uuid :amount amount})
                     {})))

           (GET "/:id" [id]
                (database/get-player {:player_id id}))
  
           (route/not-found "Not Found"))

  (context "/game" []
           (GET "/" []
                (database/get-round))

           (route/not-found "Not Found"))

  (context "/users-bets" []
           (GET "/" []
                (database/get-users-bets-rd))
           
           (route/not-found "Not Found"))

  (context "/bet" []
           (POST "/"
                 request 
                 (let [bets_uuid (get-in request [:body "bets_uuid"])
                       player_id (get-in request [:body "player_id"])
                       round_id  (get-in request [:body "round_id"])
                       cost      (get-in request [:body "cost"])
                       balls     (get-in request [:body "balls"])]
                   (do
                     (database/set-bet! {:bets_uuid bets_uuid :player_id player_id :round_id round_id :cost cost})
                     (doseq [ball balls] (database/insert-round-balls! {:bets_uuid bets_uuid :ball_number ball}))
                     {})))
           
           (route/not-found "Not Found"))
)

(defn init [] (let [init-state (database/get-state)]
                  (scheduler/change-game-bets-confirm (empty? init-state))
                  ))

(defn -main
  "Starting server"
  [& args]
  (do
    (init)
    (immutant/run (json/wrap-json-response (json/wrap-json-body (handler/api app-routes)))  {:host "localhost" :port 8080 :path "/"})))
