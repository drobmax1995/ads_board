(ns board.dao.protocol.userprotocol)

(defprotocol user-rep-protocol
	(create [this params])
	(read [this username])
	(all [this])
	(delete [this username]))