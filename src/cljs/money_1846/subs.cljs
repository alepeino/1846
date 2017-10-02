(ns money-1846.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :corporations
 (fn [db]
   (:corporations db)))

(rf/reg-sub
  :in-game-corporations
  :<- [:corporations]
  (fn [corporations]
    (filter (comp :in-game second) corporations)))

(rf/reg-sub
  :players
  (fn [db]
    (:players db)))
