(ns board.dao.connector)

(def db {  :subprotocol "mysql"
           :subname "//localhost:3306/board"
           :user "root"
           :password ""
           :zeroDateTimeBehavior "convertToNull"})
