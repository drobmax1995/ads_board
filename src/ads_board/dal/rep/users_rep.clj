(ns ads-board.dal.rep.users-rep
	; ;;(:gen-class)
 ;  (:import [java.sql SQLException])
	(:require [ads-board.dal.rep-protocol.users-protocol :as users-protocol] 
		      [ads-board.dal.rep-protocol.base-protocol :as base-protocol]
			  [ads-board.dal.dto.user :as user-dto]
			  [clojure.java.jdbc.sql :as sql]
			  [clojure.java.jdbc :as jdbc]
			  [ads-board.views :as view]))

; (defmacro try-sql
;   [& args]
;   `(try
;   	(~@args)
;      (catch SQLException e#
;        (view/login))));(jdbc/print-sql-exception-chain e#))))

(deftype users-rep [db-spec]

	;;base-rep-protocol implementaiton
	base-protocol/base-rep-protocol

	(get-items [this] 
		(jdbc/query db-spec 
            ["SELECT user_id, login, password, name, last_name, birth_date, email, address, phone FROM users"]
             :row-fn #(user-dto/->user
			 	(:user_id %1)
			 	(:login %1)
			 	(:password %1)
			 	(:name %1)
			 	(:last_name %1)
			 	(:birth_date %1)
			 	(:email %1)
			 	(:address %1)
			 	(:phone %1))))

	(get-item [this id]
		(jdbc/query db-spec
             ["SELECT user_id, login, password, name, last_name, birth_date, email, address, phone FROM users WHERE user_id = ?" id]
             :row-fn #(user-dto/->user
			 	(:user_id %1)
			 	(:login %1)
			 	(:password %1)
			 	(:name %1)
			 	(:last_name %1)
			 	(:birth_date %1)
			 	(:email %1)
			 	(:address %1)
			 	(:phone %1))))

	(insert-item [this newItem]
		(jdbc/insert! db-spec :users {
				:login (:login newItem) 
				:password (:password newItem) 
				:name (:name newItem)
				:last_name (:last_name newItem)
				:birth_date (:birth_date newItem)
				:email (:email newItem)
				:address (:address newItem)
				:phone (:phone newItem)}))

	(update-item [this updatedItem] 
		(jdbc/update! db-spec :Users{
				:login (:login updatedItem) 
				:password (:password updatedItem) 
				:name (:name updatedItem)
				:last_name (:last_name updatedItem)
				:birth_date (:birth_date updatedItem)
				:email (:email updatedItem)
				:address (:address updatedItem)
				:phone (:phone updatedItem)}
			["user_id = ?" (:user_id updatedItem)]))

	(delete-item [this id]
		(jdbc/delete! db-spec :users ["user_id = ?" id]))

	;;users-rep-protocol implementation
	users-protocol/users-rep-protocol

	(get-by-email [this email]
		(jdbc/query db-spec 
             ["SELECT user_id, login, password, name, last_name, birth_date, email, address, phone FROM users WHERE email = ?" email]
             :row-fn #(user-dto/->user
			 	(:user_id %1)
			 	(:login %1)
			 	(:password %1)
			 	(:name %1)
			 	(:last_name %1)
			 	(:birth_date %1)
			 	(:email %1)
			 	(:address %1)
			 	(:phone %1)))))