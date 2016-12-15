(defproject board "0.1"
  :description "The Board, Clojure project"
  :url "https://github.com/ArtemKravchenko94/Board"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [org.clojure/java.jdbc "0.3.0-alpha5"]
                 [mysql/mysql-connector-java "5.1.25"]
                 [selmer "1.0.9"]
                 [com.draines/postal "2.0.2"]
                 [markdown-clj "0.9.89"]
                 [metosin/ring-http-response "0.8.0"]
                 [bouncer "1.0.0"]
                 [clj-gatling "0.8.4"]
                 [noir "1.0.0" :exclusions [org.clojure/clojure hiccup]]]

  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler board.handler/app}
  :main board.test.core
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
