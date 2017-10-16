(ns money-1846.db)

(def stock-chart-values [0 10 20 30
                         40 50 60 70 80 90
                         100 112 124 137 150
                         165 180 195 212 230 250
                         270 295 320 345 375
                         405 440 475 510 550])

(defrecord Corporation
  [id name in-game stock-price portfolio cash price-last-updated])

(defrecord Player
  [id name portfolio cash])

(defn make-Corporation [id attrs]
  (map->Corporation (merge {:id id
                            :stock-price 0
                            :portfolio {id 10}
                            :cash 0}
                     attrs)))

(def default-db
  {:sidebar-open false
   :players {}
   :corporations {:baor (make-Corporation :baor {:name "Baltimore and Ohio Railroad"})
                  :ilcr (make-Corporation :ilcr {:name "Illinois Central Railroad"})
                  :nycr (make-Corporation :nycr {:name "New York Central Railroad"})
                  :caor (make-Corporation :caor {:name "Chesapeake and Ohio Railroad"})
                  :erie (make-Corporation :erie {:name "Erie Railroad"})
                  :gtrw (make-Corporation :gtrw {:name "Grand Trunk Railway"})
                  :penn (make-Corporation :penn {:name "Pennsylvania Railroad"})}})
