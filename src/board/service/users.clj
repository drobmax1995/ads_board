(ns board.service.users
  (:require [postal.core :refer [send-message]]
            [noir.util.crypt :as crypt] 
            [board.dao.user :as user]))

(def userdao (user/->user-rep))

(def emailfrom "stavar094@gmail.com")
(def pass "fpfpfp2016")

(def conn {:host "smtp.gmail.com"
           :ssl true
           :user emailfrom
           :pass pass})

(defn encrypt [{password :password :as user}]
  (assoc user :password (crypt/encrypt password)))

(defn admin? [session]
  (get session :admin))

(defn registration [{{:keys [username password email] :as user} :params} success error]
  (try 
    (if-not (.read userdao username)
      (do
        (.create userdao (encrypt (merge user {:role "user"})))
          (let [myag (agent ())]
            (send-off myag (send-message conn {:from emailfrom
                    :to email
                    :subject "Welcome to our portal"
                    :body (str "Hello," username)})))
            (success))
      (error))
  (catch Exception exception
    (error))))

(defn login [{{:keys [username password] :as user} :params session :session} error success]
  (let [{id :id stored-pass :password userrole :role} (.read userdao username)]
    (if (and stored-pass (crypt/compare password stored-pass))
      (do
        (if (= userrole "admin")
          (-> (success) (assoc :session (assoc session :user_id id :admin true :username username)))
          (-> (success) (assoc :session (assoc session :user_id id :username username)))))
      (error))))

(defn logout [{session :session} success]
  (->(success) (assoc :session (assoc session :admin nil :user_id nil :username nil))))


