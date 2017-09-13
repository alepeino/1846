(ns money-1846.views
  (:require
    [reagent.core :as r]
    [re-frame.core :as re-frame]))

(defn sidebar []
  (let [open (r/atom false)]
    (fn []
      [:div#sidebar
       [:nav.p-1.bg-danger
        [:button.navbar-toggler.align-self-start
          {:type "button"
           :onClick #(swap! open not)}
          [:span.navbar-toggler-icon.small]]]
       [:div.sidebar-content.w-50.bg-info {:class (when @open "open")}]])))

(defn main []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div
       [sidebar]
       [:div.content "Hello from " @name]])))
