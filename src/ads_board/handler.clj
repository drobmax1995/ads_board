(ns ads-board.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ads-board.user :as user]
            [ads-board.post :as post]
            [ads-board.category :as category]
            [ads-board.feadback :as feadback]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/user" [] (user/read-all))
  (GET "/post" [] (post/read-all))
  (GET "/feadback" [] (feadback/read-all))
  (GET "/category" [] (category/read-all))
  (GET "/" [] "hello world!")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
