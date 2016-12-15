(ns board.views.view
  (:require [board.service.posts :as posts]
        [board.service.users :as users]
        [board.service.comments :as comments]
        [board.layout :as layout]))
  (use 'selmer.parser)

(defn main [{session :session} res] 
  (layout/render "main.html" {:posts (posts/all) :session session :dsl-message res}))

(defn signup [] 
  (layout/render "signup.html"))

(defn login [] 
  (layout/render "login.html"))

(defn add-post [] 
  (layout/render "add-post.html"))

(defn view-post [{{:keys [id] :as id} :params session :session}]
  (layout/render "post.html" {:comments (comments/all id) :post (posts/read id) :session session}))

(defn edit-post [{{:keys [id] :as id} :params}] 
  (layout/render "edit-post.html" {:post (posts/read id)}))

(defn not-auth [] 
  (layout/render "error.html" (merge {:status 403 :title "You are not authorized."})))