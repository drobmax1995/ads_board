(ns board.handler
  (:require [compojure.core :refer :all]
  			    [compojure.handler :as handler]
            [compojure.route :as route]
            [board.views.view :as views]
            [board.service.posts :as posts]
            [ring.util.response :refer [redirect]]
            [board.layout :as layout]
            [board.service.comments :as comments]
            [board.service.users :as users]
            [board.service.dsl :as dsl]
            [board.middleware :refer [wrap-internal-error]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.defaults :refer :all]))

(defroutes public-routes

  (GET "/posts/add" request
    (if (get-in request [:session :admin])
        (views/add-post)
        (views/not-auth)))

  (POST "/posts/add" request
    (if (get-in request [:session :admin])
          (do (posts/create request) 
            (redirect "/"))
          (views/not-auth)))

  (GET "/query" request
    (if (get-in request [:session :admin])
      (let [res (dsl/run (str "(" (get-in request [:params :query]) ")"))]
        (do 
          (println res)
          (views/main request res)))
      (views/not-auth)))

  (GET "/posts/:id/edit" request
    (if (get-in request [:session :admin])
          (views/edit-post request)
          (views/not-auth)))

  (POST "/posts/:id/save" request
    (if (get-in request [:session :admin])
          (do (posts/update request)
            (redirect "/"))
          (views/not-auth)))

  (GET "/posts/:id/delete" request
    (if (get-in request [:session :admin])
            (do (posts/delete request)
              (redirect "/"))
            (views/not-auth)))
  
  (GET "/comments/:id/delete" request
    (if (get-in request [:session :admin])
      (do (comments/delete request) (redirect (get-in request [:headers "referer"])))
      (views/not-auth)))

  (GET "/" request (views/main request ""))
  (GET "/login" [] (views/login))
  (POST "/login" request (users/login 
    request #(layout/render "login.html" (merge {:error-message "no user with such credentials"})) #(redirect "/")))

  (GET "/signup" [] (views/signup))
  (POST "/signup" request (users/registration
    request #(redirect "/") #(layout/render "signup.html" (merge {:error-message "User with such credentials already exists."}))))

  (GET "/posts/:id" request (views/view-post request))
  (GET "/logout" request (users/logout request #(redirect "/")))

  (POST "/comments/create" request 
    (if (get-in request [:session :user_id])
      (do (comments/create request) 
        (redirect (str "/posts/" (get-in request [:params :post_id]))))
      (views/not-auth)))

(route/resources "/"))

(defroutes app-routes
  public-routes
  (route/not-found 
    (:body (layout/error-page {:status 404 :title "Page not found"}))))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (wrap-internal-error)))

