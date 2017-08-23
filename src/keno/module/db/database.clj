(ns keno.module.db.database
  (:require [yesql.core :refer [defquery]]))

(def db-spec {:classname "com.mysql.jdbc.Driver"
              :subprotocol "mysql"
              :subname "//172.17.0.2:3306/keno" ;; docker mysql default first ip image mysql
              :user "root"
              :password "123"})

(defquery res "sql/bets.sql" {:connection db-spec})
(defquery save-round-balls! "sql/insert-round-balls.sql" {:connection db-spec})
(defquery get-round "sql/get-round.sql" {:connection db-spec})


