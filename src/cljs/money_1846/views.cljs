(ns money-1846.views
  (:require
    [money-1846.components.core :as components]
    [money-1846.db :as db]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn sidebar-menu []
  [:nav
   [:div.menu-section.bg-lighter.rounded.m-4.p-1
    [:ul.list-unstyled.text-center
     [:li
      [components/collapsible-panel {:class "nav-link text-center text-light h5"}
       "Add Corporation"
       [components/add-remove-corporations]]]
     [:li.px-4 [:hr.m-1]]
     [:li
      [components/collapsible-panel {:class "nav-link text-center text-light"}
       "Add Player"
       [:div ""]]]]]])

(defn game-panel [in-game-corporations]
  [:div
   [components/stock-price-chart in-game-corporations db/stock-chart-values]])

(defn main []
  (let [in-game-corporations (rf/subscribe [:in-game-corporations])]
    [:div
     [components/sidebar sidebar-menu]
     [:div
      [game-panel @in-game-corporations]
      [:div (pr-str @in-game-corporations)]]]))
