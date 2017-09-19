(ns money-1846.views
  (:require
    [money-1846.components.core :refer [sidebar collapsible-panel]]
    [money-1846.components.game-components :as game-components]
    [money-1846.db :as db]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn sidebar-menu []
  [:nav
   [:div.menu-section.bg-lighter.rounded.m-4.p-1
    [:ul.list-unstyled.text-center
     [:li
      [collapsible-panel {:class "nav-link text-center text-light h5"}
       "Add Corporation"
       [game-components/add-remove-corporations]]]
     [:li.px-4 [:hr.m-1]]
     [:li
      [collapsible-panel {:class "nav-link text-center text-light"}
       "Add Player"
       [:div ""]]]]]])

(defn game-panel []
  [:div
   [game-components/stock-scale db/stock-scale]])

(defn main []
  (let [in-game-corporations (rf/subscribe [:in-game-corporations])]
    [:div
     [sidebar sidebar-menu]
     [:div
      [game-panel]
      [:div (pr-str @in-game-corporations)]]]))
