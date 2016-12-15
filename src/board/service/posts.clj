(ns board.service.posts  
  (:require [board.dao.post :as post]))

(def postdao (post/->post-rep))

(def cache (atom {}))

(defn create [{{:keys [title content] :as post} :params}]
    (let [id (get (first (.create postdao (merge post {:created_at (str (java.sql.Timestamp. (System/currentTimeMillis)))}))) 1)]
         (swap! cache conj (assoc post :id id))))

(defn read [id]
  (let [cached ((keyword id) @cache)]
    (if (nil? cached)
      (let [post (.read postdao id)]
        (swap! cache conj {(keyword id) post}) 
      post)
      cached)))

(defn all []
  (if (= 0 (count @cache)) 
    (let [posts (.all postdao)]
      (reset! cache (sort-by :created_at #(compare %2 %1) (.all postdao)))
    posts)
    @cache))
  ; (reset! cache (sort-by :created_at #(compare %2 %1) (.all postdao))))

(defn update [{{:keys [title content] :as post, id :id} :params}]
	 (do
        (swap! cache conj {(keyword id) post})
        (.update postdao id post)))

(defn delete [{{:keys [id] :as id} :params}]
	(.delete postdao id)
	(swap! cache  conj (dissoc (keyword (:id id)))))