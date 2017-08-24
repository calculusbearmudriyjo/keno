(defproject keno "0.1.0-SNAPSHOT"
  :description "keno engine"
  :url "not"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.immutant/immutant "2.1.9"]
                 [compojure "1.6.0"]
                 [mysql/mysql-connector-java "5.1.32"]
                 [yesql "0.5.3"]
                 [ring/ring-json "0.4.0"]] 
  :main ^:skip-aot keno.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
