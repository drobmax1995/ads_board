(ns board.service.dsl
    (:require [board.dao.user :as user]))

(def userdao (user/->user-rep))

(defn show [user-key username]  ;show :user "username"
  (if (= :user user-key)
      (let [usr (.read userdao username)]
          (if usr
              (let [{id :id role :role} usr] (str "username: " username "; id: " id "; role: " role))
              (str "no such user: " username)))
      (str "no such command: " user-key)))

(defn delete [user-key username]  ;delete :user "username"
  (if (= :user user-key)
      (let [result (.delete userdao username)]
          (if result
              (str "user \"" username "\" was deleted!")
              (str "no such user: " username)))
      (str "no such command: " user-key)))
    
(defn gcc [string]
  (try
    (load-string (str "(board.service.dsl/" string ")"))
  (catch Exception exception 
    (str "unknown command"))))
