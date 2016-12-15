(ns board.test.core
  (:require [clj-gatling.core :as gatling]
            [board.test.simulation :refer [simulations]]
            [clj-time.core :as time])
  (:gen-class))

(defn -main [simulation users requests & [option]]
  (let [simulation (or ((keyword simulation) simulations)
                       (throw (Exception. (str "No such simulation " simulation))))]
    
    (gatling/run simulation
                 {:concurrency (read-string users)
                  :root "tmp"
                  :requests (read-string requests)})))