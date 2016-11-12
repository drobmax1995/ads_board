(ns ads-board.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.util.response :as resp]
            [ads-board.layout :as layout]
      
            [ads-board.controllers.ad :as ad]
            [ads-board.controllers.category :as category]
            [ads-board.controllers.feadback :as feadback]

 
            ;;import for users

            [ads-board.dal.db-conf :as db]
            [ads-board.logic.services.users-service :as users-service]
            [ads-board.dal.dto.user :as user]
            [ads-board.dal.rep.users-rep :as users-repo]


            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def users-repository (users-repo/->users-rep db/db-spec))
(def users-service (users-service/->users-service users-repository))


(defroutes app-routes
 ;; (GET "/user/:id" [id] (user/show id))
  (GET "/users" [] (.get-items users-service))
  (GET "/ads" [] (ad/ads))
  (GET "/feadback" [] (feadback/feadbacks))
  (GET "/category" [] (category/categories))
  (GET "/" [] (user/index-page))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))