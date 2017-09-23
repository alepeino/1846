(ns money-1846.events
  (:require [re-frame.core :as re-frame]
            [money-1846.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
  :corporations/add
  (fn [db [_ id]]
    (assoc-in db [:corporations id :in-game] true)))

(re-frame/reg-event-db
  :corporations/remove
  (fn [db [_ id]]
    (assoc-in db [:corporations id :in-game] false)))

(re-frame/reg-event-db
  :corporations/set-price
  (fn [db [_ id price]]
    (assoc-in db [:corporations id :stock-price] price)))
