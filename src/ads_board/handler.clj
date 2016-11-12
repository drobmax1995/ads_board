(ns ads-board.handler
  (:require [compojure.core :refer :all]
            [ads-board.layout :refer [error-page]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.util.response :as resp]
            [ads-board.layout :as layout]
            [ads-board.controllers.user :as user]
            [ads-board.controllers.ad :as ad]
            [ads-board.controllers.category :as category]
            [ads-board.controllers.feadback :as feadback]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/user/:id" [id] (user/show id))
  (GET "/users" [] (user/index))
  (GET "/ads" [] (ad/ads))
  (GET "/feadback" [] (feadback/feadbacks))
  (GET "/category" [] (category/categories))
  (GET "/" [] (layout/render "simple.html"))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
