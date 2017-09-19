(ns money-1846.db)

(def stock-scale [0 10 20 30
                  40 50 60 70 80 90
                  100 112 124 137 150
                  165 180 195 212 230 250
                  270 295 320 345 375
                  405 440 475 510 550])

(defrecord Corporation
  [id name in-game stock-price])

(def default-db
  {:corporations {:baor (map->Corporation {:id :baor :name "Baltimore and Ohio Railroad"})
                  :ilcr (map->Corporation {:id :ilcr :name "Illinois Central Railroad"})
                  :nycr (map->Corporation {:id :nycr :name "New York Central Railroad"})
                  :caor (map->Corporation {:id :caor :name "Chesapeake and Ohio Railroad"})
                  :erie (map->Corporation {:id :erie :name "Erie Railroad"})
                  :gtrw (map->Corporation {:id :gtrw :name "Grand Trunk Railway"})
                  :penn (map->Corporation {:id :penn :name "Pennsylvania Railroad"})}})
