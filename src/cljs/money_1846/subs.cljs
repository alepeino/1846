(ns money-1846.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as rf]))

(rf/reg-sub :sidebar-open :sidebar-open)

(rf/reg-sub :players :players)

(rf/reg-sub :corporations :corporations)

(rf/reg-sub
  :in-game-corporations
  :<- [:corporations]
  (fn [corporations]
    (filter (comp :in-game second) corporations)))
