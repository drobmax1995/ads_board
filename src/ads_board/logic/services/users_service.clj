(ns ads-board.logic.services.users-service
	(:require [ads-board.logic.services-protocols.base-protocol :as base-protocol]
			  [ads-board.logic.services-protocols.users-protocol :as users-protocol]
			  [ads-board.dal.rep.users-rep :as users-repo]))

(deftype users-service [users-repo] 

	base-protocol/base-service-protocol

	(get-items [this] 
		(.get-items users-repo))

	(get-item [this id]
		(first (.get-item users-repo id)))

	(insert-item [this newItem]
		(.insert-item users-repo newItem))

	(update-item [this updatedItem]
		(.update-item users-repo updatedItem))

	(delete-item [this id]
		(.delete-item users-repo id))

	users-protocol/users-service-protocol

	(get-by-email [this email]
		(.get-by-email users-repo email)))
