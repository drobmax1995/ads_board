(ns ads-board.logic.services.posts-service
	(:require [ads-board.logic.services-protocols.base-protocol :as base-protocol]
			  [ads-board.logic.services-protocols.posts-protocol :as posts-protocol]
			  [ads-board.dal.rep.posts-rep :as posts-repo]))

(defn now [] (new java.util.Date))

(deftype posts-service [posts-repo] 

	base-protocol/base-service-protocol

	(get-items [this] 
		(.get-items posts-repo))

	(get-item [this id]
		(first (.get-item posts-repo id)))

	(insert-item [this newItem]
		(.insert-item posts-repo (assoc newItem :created_at (now) :updated_at (now))))

	(update-item [this updatedItem]
		(.update-item posts-repo (assoc updatedItem :updated_at (now))))

	(delete-item [this id]
		(.delete-item posts-repo id))

	posts-protocol/posts-service-protocol

	(get-by-user-id [this user_id]
		(.get-by-user-id posts-repo user_id)))
