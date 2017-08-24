(ns keno.module.db.database
  (:require [yesql.core :refer [defquery]]))

(def db-spec {:classname "com.mysql.jdbc.Driver"
              :subprotocol "mysql"
              :subname "//172.17.0.2:3306/keno" ;; docker mysql default first ip image mysql
              :user "root"
              :password "123"})

(defquery winner-balls       "sql/winner-balls.sql"       {:connection db-spec})
(defquery save-round-balls!  "sql/insert-round-balls.sql" {:connection db-spec})
(defquery get-round          "sql/get-round.sql"          {:connection db-spec})

(defquery get-state          "sql/init.sql"               {:connection db-spec}) 
(defquery get-user-amount    "sql/get-user-amount.sql"    {:connection db-spec})
(defquery update-user-amount!"sql/update-user-amount.sql" {:connection db-spec})

(defquery update-user-bet!   "sql/update-user-bet.sql"    {:connection db-spec})

(defquery get-users-balls    "sql/get-users-balls.sql"    {:connection db-spec})
(defquery get-users-bets-rd  "sql/get-users-bets-rd.sql"  {:connection db-spec})
(defquery get-user-last-bets "sql/get-user-last-bets.sql" {:connection db-spec})
(defquery set-bet!           "sql/set-bet.sql"            {:connection db-spec})
(defquery insert-round-balls!"sql/insert-round-balls.sql" {:connection db-spec})

(defquery new-round "sql/new-round.sql" {:connection db-spec})

(defquery get-player "sql/get-player.sql" {:connection db-spec})
(defquery set-player!"sql/set-player.sql" {:connection db-spec})
