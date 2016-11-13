(ns ads-board.controllers.user
  (:require 
            [ads-board.layout :as layout]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]))

(def mysql-db {:subprotocol "mysql"
               :subname "//localhost:3306/ad_db"
               :user "root"
               :password "1111"
               :zeroDateTimeBehavior "convertToNull"})

(defn show [id]
  (def u (jdbc/query mysql-db
      (sql/select * :users (sql/where {:user_id id}))))
  (layout/render "users.html" (merge {:id (:login (nth u 0))}))
  )

(defn index []
  (jdbc/query mysql-db
    (sql/select * :users )))

(defn index-page [] 
  (layout/render
    "home.html" {:docs "document"}))

; (defn start []
;   (layout/render "views/simple.html"))

; (defn start
;   ([] (layout/render "signup.html"))
;   ([error] (layout/render "signup.html" (merge {:error-message error}))))
