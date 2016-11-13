(ns ads-board.dal.db-conf)

(def db-spec {:subprotocol "mysql"
               :subname "//localhost:3306/ad_db"
               :user "root"
               :password ""
               :zeroDateTimeBehavior "convertToNull"})