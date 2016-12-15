(ns board.middleware
  (:require [board.layout :refer [error-page]]))

(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
      	(prn t)
        (error-page {:status 500
                     :title "Something went wrong."
                     :message "Server is unavailable now"})))))
