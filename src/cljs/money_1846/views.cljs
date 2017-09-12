(ns money-1846.views
  (:require [re-frame.core :as re-frame]))

(defn main []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div "Hello from " @name])))
