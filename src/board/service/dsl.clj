(ns board.service.dsl
    (:require [board.dao.user :as user]))

(def userdao (user/->user-rep))

(def cur-ns ((meta #'userdao) :ns))

(defn run [string]
  (try
    (binding [*ns* cur-ns]
      (load-string string))
  (catch Exception exception
    (str "unknown command"))))

(defn show [user-key username]
  (if (= :user user-key)
      (let [usr (.read userdao username)]
          (if usr
                (str (apply str usr))
              (str "no such user: " username)))
      (str "no such command: " user-key)))

(defn show-all [user-key]
  (if (= :user user-key)
      (let [users (.all userdao)]
          (if users
              (str "all users in db:" (apply str users))
              (str "no users: " "")))
      (str "no such command: " user-key)))

(defn delete [user-key username]
  (if (= :user user-key)
      (let [result (.delete userdao username)]
          (if result
              (str "user \"" username "\" was deleted!")
              (str "no such user: " username)))
      (str "no such command: " user-key)))
