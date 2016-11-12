(ns ads-board.controllers.feadback
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]))

(def mysql-db {:subprotocol "mysql"
               :subname "//localhost:3306/ad_db"
               :user "root"
               :password "1111"
               :zeroDateTimeBehavior "convertToNull"})

(defn feadbacks []
  (jdbc/query mysql-db
    (sql/select * :feadback)))