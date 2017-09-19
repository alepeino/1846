(ns money-1846.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :corporations
 (fn [db]
   (:corporations db)))

(re-frame/reg-sub
  :in-game-corporations
  (fn [db]
    (filter (comp :in-game second) (:corporations db))))
