(ns ads-board.views
	(:use hiccup.page
		  hiccup.element
     :require [ads-board.layout :as layout]))

(defn index-page [] 
	(layout/render
    "home.html" {:docs "document"}))

;;posts

(defn all-users-page [users deleted added param]
	(layout/render
		"users/all_users.html" {:users users :deleted deleted :added added :param param}))

(defn add-user-page []
	(layout/render
		"users/add_user.html"))

(defn user-page [user updated]
	(layout/render
		"users/user.html" {:user user :updated updated}))

;;posts

(defn all-posts-page [posts deleted added param]
	(layout/render
		"posts/all_posts.html" {:posts posts :deleted deleted :added added :param param}))