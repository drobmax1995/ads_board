(ns board.utils.counter)

(def counter (ref {}))

(defn increment [key]
  (dosync (alter counter assoc key (inc (@counter key 0)))))

(defn decrement [key]
  (dosync (alter counter assoc key (dec (@counter key 0)))))

(defn rm-counter [key]
  (let [value (@counter key)]
    (if value
      (do (dosync (alter counter dissoc key))))))
  
(defn get-number []
    (count @counter))

(defn login [id]
    (if (contains? @counter (keyword (str id)))
        (dosync (increment (keyword (str id))))
        (dosync (alter counter assoc (keyword (str id)) 0))))

(defn logout [id]
    (let [n (@counter (keyword (str id)))]
         (if (> n 0)
             (dosync (decrement (keyword (str id))))
             (dosync (rm-counter (keyword (str id)))))))
             

