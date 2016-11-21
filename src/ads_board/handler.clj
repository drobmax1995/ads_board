(ns ads-board.handler
  (:use compojure.core)
  (:require [ring.util.response :as response]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
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

            ;;import for category

            [ads-board.logic.services.category-service :as category-service]
            [ads-board.dal.dto.category :as category]
            [ads-board.dal.rep.category-rep :as category-repo]

            ;;import for favorite_category

            [ads-board.logic.services.favorite-category-service :as favorite-category-service]
            [ads-board.dal.dto.favorite-category :as favorite-category]
            [ads-board.dal.rep.favorite-category-rep :as favorite-category-repo]
             

             ;;import for favorite-post

            [ads-board.logic.services.favorite-post-service :as favorite-post-service]
            [ads-board.dal.dto.favorite-post :as favorite-post]
            [ads-board.dal.rep.favorite-post-rep :as favorite-post-repo]

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

(def category-repository (category-repo/->category-rep db/db-spec))
(def category-service (category-service/->category-service category-repository))

(def favorite-category-repository (favorite-category-repo/->favorite-category-rep db/db-spec))
(def favorite-category-service (favorite-category-service/->favorite-category-service favorite-category-repository))

(def favorite-post-repository (favorite-post-repo/->favorite-post-rep db/db-spec))
(def favorite-post-service (favorite-post-service/->favorite-post-service favorite-post-repository))


(defn create-user ([login password name last_name birth_date email address phone] (user/->user nil login password name last_name birth_date email address phone))
          ([id login password name last_name birth_date email address phone] (user/->user id login password name last_name birth_date email address phone)))

(defn create-post ([user_id category_id title description status created_at updated_at] (post/->post nil user_id category_id title description status created_at updated_at))
          ([id user_id category_id title description status created_at updated_at] (post/->post id user_id category_id title description status created_at updated_at)))

(defroutes app-routes
  

  ;; users
  (GET "/users" [] (view/all-users-page (.get-items users-service) false false nil))

  (GET "/user/add" [] (view/add-user-page))

  (POST "/user/add" request (do (.insert-item users-service (user/->user
                        nil
                        (get-in request [:params :login])
                        (get-in request [:params :password])
                        (get-in request [:params :name])
                        (get-in request [:params :last_name])
                        (get-in request [:params :birth_date]) 
                        (get-in request [:params :email])
                        (get-in request [:params :address]) 
                        (get-in request [:params :phone]))) 
                  (response/redirect "/users")))

  (POST "/user/update" request (do (.update-item users-service (user/->user
                        (get-in request [:params :user_id]) 
                        (get-in request [:params :login]) 
                        (get-in request [:params :password])
                        (get-in request [:params :name]) 
                        (get-in request [:params :last_name])
                        (get-in request [:params :birth_date]) 
                        (get-in request [:params :email])
                        (get-in request [:params :address]) 
                        (get-in request [:params :phone]))) 
                  (response/redirect "/users")))

  (POST "/user/delete" request (do (.delete-item users-service 
                        (get-in request [:params :user_id]))
                 (response/redirect "/users")))

  (GET "/user/:id" [id] (view/user-page (.get-item users-service id) false))

  (GET "/login" [] (view/login))
  
  (GET "/posts" [] (view/all-posts-page (.get-items posts-service) false false nil))

  (GET "/post/add" [] (view/add-post-page))

  (POST "/post/add" request (do (.insert-item posts-service (post/->post
                        nil 
                  (get-in request [:params :user_id])
                  (get-in request [:params :category_id])
                  (get-in request [:params :title])
                  (get-in request [:params :status])
                  (get-in request [:params :description])
                  (get-in request [:params :created_at])
                  (get-in request [:params :updated_at])))
                  (response/redirect "/posts")))

  (POST "/post/update" request (do (.update-item posts-service (post/->post
                  (get-in request [:params :post_id]) 
                  (get-in request [:params :user_id])
                  (get-in request [:params :category_id])
                  (get-in request [:params :title])
                  (get-in request [:params :status])
                  (get-in request [:params :description])
                  (get-in request [:params :created_at])
                  (get-in request [:params :updated_at])))
                  (response/redirect "/posts")))

  (POST "/post/delete" request (do (.delete-item posts-service 
                        (get-in request [:params :post_id]))
                 (response/redirect "/posts")))

  (GET "/post/:id" [id] (view/post-page (.get-item posts-service id) false))

  ;;comments

  (GET "/feadback" [] (view/all-feadbacks-page (.get-items feadback-service) false false nil))

  (GET "/feadback/add" [] (view/add-feadback-page))

  (POST "/feadback/add" request (do (.insert-item feadback-service (feadback/->feadback
                        nil
                        (get-in request [:params :post_id])
                        (get-in request [:params :user_id])
                        (get-in request [:params :created_at])
                        (get-in request [:params :body]))) 
                  (response/redirect "/feadback")))

  (POST "/feadback/update" request (do (.update-item feadback-service (feadback/->feadback
                        (get-in request [:params :feadback_id])
                        (get-in request [:params :post_id])
                        (get-in request [:params :user_id])
                        (get-in request [:params :created_at])
                        (get-in request [:params :body])))
                  (response/redirect "/feadback")))

  (POST "/feadback/delete" request (do (.delete-item feadback-service 
                        (get-in request [:params :feadback_id]))
                 (response/redirect "/feadback")))

  (GET "/feadback/:id" [id] (view/feadback-page (.get-item feadback-service id) false))

  ;;category

  (GET "/categories" [] (view/all-categories-page (.get-items category-service) false false nil))

  (GET "/category/add" [] (view/add-category-page))

  (POST "/category/add" request (do (.insert-item category-service (category/->category
                        nil
                        (get-in request [:params :c_name]))) 
                  (response/redirect "/categories")))

  (POST "/category/update" request (do (.update-item category-service (category/->category
                        (get-in request [:params :category_id])
                        (get-in request [:params :c_name])))
                  (response/redirect "/categories")))

  (POST "/category/delete" request (do (.delete-item category-service 
                        (get-in request [:params :category_id]))
                 (response/redirect "/categories")))

  (GET "/category/:id" [id] (view/category-page (.get-item category-service id) false))

  

  (GET "/" [] (layout/render
    "home.html" {:docs "document"}))
  (route/not-found "Not Found"))

; (def app
;   (wrap-defaults app-routes site-defaults))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))