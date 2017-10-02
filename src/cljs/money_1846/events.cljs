(ns money-1846.events
  (:require
    [re-frame.core :as rf]
    [money-1846.db :as db]))

(rf/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(rf/reg-event-db
  :corporations/add
  (fn [db [_ id]]
    (assoc-in db [:corporations id :in-game] true)))

(rf/reg-event-db
  :corporations/remove
  (fn [db [_ id]]
    (assoc-in db [:corporations id :in-game] false)))

(rf/reg-event-db
  :corporations/set-price
  (fn [db [_ id price]]
    (-> db
      (assoc-in [:corporations id :stock-price] price)
      (assoc-in [:corporations id :price-last-updated] (js/Date.now)))))

(rf/reg-event-db
  :player/add
  (fn [db [_ player-name]]
    (let [player-count (-> db :players count)
          player-id (str "player" (inc player-count))]
      (assoc-in db [:players player-id] (db/map->Player {:id player-id :name player-name})))))
