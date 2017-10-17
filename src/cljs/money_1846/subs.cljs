(ns money-1846.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as rf]))

(rf/reg-sub :sidebar-open :sidebar-open)

(rf/reg-sub :bank :bank)

(rf/reg-sub :players :players)

(rf/reg-sub
  :players-computed-portfolio
  :<- [:players]
  (fn [players]
    (into {}
      (for [[id player] players]
        [id (-> player (assoc :portfolio-total 123))]))))

(rf/reg-sub :corporations :corporations)

(rf/reg-sub
  :in-game-corporations
  :<- [:corporations]
  (fn [corporations]
    (filter (comp :in-game second) corporations)))
