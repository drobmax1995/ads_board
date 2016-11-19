(ns ads-board.handler
  (:require [ring.util.response :as response]
            [ring.middleware.json :as middleware]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [clojure.java.io :as io]
            [ring.util.response :as resp]
            [ads-board.layout :as layout]
            [ads-board.views :as view]
 
            [ads-board.dal.db-conf :as db]

            ;;import for users

            [ads-board.logic.services.users-service :as users-service]
            [ads-board.dal.dto.user :as user]
            [ads-board.dal.rep.users-rep :as users-repo]
             

             ;;import for feadback

            [ads-board.logic.services.feadback-service :as feadback-service]
            [ads-board.dal.dto.feadback :as feadback]
            [ads-board.dal.rep.feadback-rep :as feadback-repo]


             ;;import for posts

            [ads-board.logic.services.posts-service :as posts-service]
            [ads-board.dal.dto.post :as post]
            [ads-board.dal.rep.posts-rep :as posts-repo]))

(def users-repository (users-repo/->users-rep db/db-spec))
(def users-service (users-service/->users-service users-repository))

(def posts-repository (posts-repo/->posts-rep db/db-spec))
(def posts-service (posts-service/->posts-service posts-repository))

(def feadback-repository (feadback-repo/->feadback-rep db/db-spec))
(def feadback-service (feadback-service/->feadback-service feadback-repository))


(defn create-user ([login password name last_name birth_date email address phone] (user/->user nil login password name last_name birth_date email address phone))
          ([id login password name last_name birth_date email address phone] (user/->user id login password name last_name birth_date email address phone)))


(defroutes app-routes
 ;; (GET "/user/:id" [id] (user/show id))


  (GET "/users" [] (view/all-users-page (.get-items users-service) false false nil))
  
  (GET "/posts" [] (view/all-posts-page (.get-items posts-service) false false nil))

  (GET "/user/add" [] (view/add-user-page))

  (POST "/user/add" request (do (.insert-item users-service (create-user 
                        (get-in request [:params :login]) 
                        (get-in request [:params :password])
                        (get-in request [:params :name]) 
                        (get-in request [:params :last_name])
                        (get-in request [:params :birth_date]) 
                        (get-in request [:params :email])
                        (get-in request [:params :address]) 
                        (get-in request [:params :phone]))) 
                  (response/redirect (str "/users/" (get-in request [:params :id]) "/false/true"))))

  (GET "/user/:id" [id] (view/user-page (.get-item users-service id) false))

  (GET "/feadback" [] (view/all-feadback-page (.get-items feadback-service) false false nil))

  (GET "/" [] (layout/render
    "home.html" {:docs "document"}))
  (route/not-found "Not Found"))

; (def app
;   (wrap-defaults app-routes site-defaults))

(def app  
  (-> (handler/site app-routes) (middleware/wrap-json-body {:keywords? true})))
