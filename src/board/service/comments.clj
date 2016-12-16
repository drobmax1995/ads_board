(ns board.service.comments
  (:use compojure.core)
  (:require [board.dao.comment :as comment]))

(def commentdao (comment/->comment-rep))

(defn me [session]
  (get session :user_id))

(defn create [{{:keys [post_id content] :as comment} :params session :session}]
  (.create commentdao
  	(merge comment {:created_at (str (java.sql.Timestamp. (System/currentTimeMillis))) :user_id (me session)})))

(defn read [id]
  (.read commentdao id))

(defn all [post_id]
  (sort-by :created_at #(compare %1 %2) 
    (.all commentdao post_id)))

(defn delete [{{:keys [id] :as id} :params}]
  (let [{post_id :post_id} (.read commentdao id)]
	  (.delete commentdao id)))