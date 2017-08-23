(ns keno.core
  (:gen-class)
  (:require [compojure.route :as route]
            [immutant.web :as immutant]))

(defn app [request]
  {:status 200
   :body "test"})

(defn -main
  "Starting server"
  [& args]
  (immutant/run app  {:host "localhost" :port 8080 :path "/"}))
