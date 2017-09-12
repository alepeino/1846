(ns money-1846.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [money-1846.events]
            [money-1846.subs]
            [money-1846.views :as views]
            [money-1846.config :as config]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
