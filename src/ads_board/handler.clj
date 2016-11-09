(ns ads-board.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ads-board.user :as user]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (user/read))
  (GET "/all" [] (user/read-all))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
