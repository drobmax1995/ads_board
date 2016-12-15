(ns board.test.simulation
  (:require [clojure.core.async :refer [chan go >!]]
            [org.httpkit.client :as http]))

(def options {:timeout 2000 
              :form-params (sorted-map :username "test"
                                       :password "test")})


(def base-url "http://localhost:3000")

(defn- http-get [url _]
  (let [response (chan)
        check-status (fn [{:keys [status]}]
                       (go (>! response (= 200 status))))]
    (http/get (str base-url url) {} check-status)
    response))

(defn- http-post [url _]
  (http/post (str base-url url) options))

(def signup
  (partial http-post "/signup"))

(def ping
  (partial http-get "/"))

(def ping-simulation
  {:name "Ping simulation"
   :scenarios [{:name "Ping scenario"
                :steps [{:name "Ping Endpoint" :request ping}]}]})

(def signup-simulation
  {:name "Signup simulation"
   :scenarios [{:name "Signup scenario"
                :steps [{:name "Signup Endpoint" :request signup}]}]})

(def simulations
  {:ping ping-simulation
   :signup signup-simulation})