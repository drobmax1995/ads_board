(ns ads-board.dal.rep.posts-rep
	(:require [ads-board.dal.rep-protocol.posts-protocol :as posts-protocol] 
		      [ads-board.dal.rep-protocol.base-protocol :as base-protocol]
			  [ads-board.dal.dto.post :as post-dto]
			  [clojure.java.jdbc.sql :as sql]
			  [clojure.java.jdbc :as jdbc]))

(deftype posts-rep [db-spec]

	;;base-rep-protocol implementaiton
	base-protocol/base-rep-protocol

	(get-items [this] 
		(jdbc/query db-spec 
            ["SELECT post_id, user_id, category_id, title, description, status, created_at, updated_at FROM posts"]
             :row-fn #(post-dto/->post
			 	(:post_id %1)
			 	(:user_id %1)
			 	(:category_id %1)
			 	(:title %1)
			 	(:description %1)
			 	(:status %1)
			 	(:created_at %1)
			 	(:updated_at %1))))

	(get-item [this id]
		(jdbc/query db-spec
             ["SELECT post_id, user_id, category_id, title, description, status, created_at, updated_at FROM posts WHERE post_id = ?" id]
              :row-fn #(post-dto/->post
			 	(:post_id %1)
			 	(:user_id %1)
			 	(:category_id %1)
			 	(:title %1)
			 	(:description %1)
			 	(:status %1)
			 	(:created_at %1)
			 	(:updated_at %1))))

	(insert-item [this newItem]
		(jdbc/insert! db-spec :posts {
				:user_id (:user_id newItem) 
				:category_id (:category_id newItem)
				:title (:title newItem)
				:description (:description newItem)
				:status (:status newItem)
				:created_at (:created_at newItem)
				:updated_at (:updated_at newItem)}))

	(update-item [this updatedItem] 
		(jdbc/update! db-spec :posts{
				:user_id (:user_id updatedItem) 
				:category_id (:category_id updatedItem) 
				:title (:title updatedItem)
				:description (:description updatedItem)
				:status (:status updatedItem)
				:created_at (:created_at updatedItem)
				:updated_at (:updated_at updatedItem)}
			["post_id = ?" (:post_id updatedItem)]))

	(delete-item [this id]
		(jdbc/delete! db-spec :users ["post_id = ?" id]))

	;;posts-rep-protocol implementation
	posts-protocol/posts-rep-protocol

	(get-by-user-id [this user_id]
		(jdbc/query db-spec 
             ["SELECT post_id, user_id, category_id, title, description, status, created_at, updated_at FROM posts WHERE user_id = ?" user_id]
             :row-fn #(post-dto/->post
			 	(:post_id %1)
			 	(:user_id %1)
			 	(:category_id %1)
			 	(:title %1)
			 	(:description %1)
			 	(:status %1)
			 	(:created_at %1)
			 	(:updated_at %1)))))