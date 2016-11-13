(ns ads-board.views
	(:use hiccup.page
		  hiccup.element
     :require [ads-board.layout :as layout]))

(defn index-page [] 
	(layout/render
    "home.html" {:docs "document"}))

(defn all-users-page [users deleted added param]
	(layout/render
		"users/all_users.html" {:users users :deleted deleted :added added :param param}))

(defn add-user-page []
	(layout/render
		"users/add_user.html"))