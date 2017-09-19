(ns money-1846.events
  (:require [re-frame.core :as re-frame]
            [money-1846.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
  :add-corporation
  (fn [db [_ id]]
    (assoc-in db [:corporations id :in-game] true)))

(re-frame/reg-event-db
  :remove-corporation
  (fn [db [_ id]]
    (assoc-in db [:corporations id :in-game] false)))
