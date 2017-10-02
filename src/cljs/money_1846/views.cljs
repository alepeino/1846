(ns money-1846.views
  (:require
    [money-1846.components.corporations-panel :refer [corporations-panel]]
    [money-1846.components.stock-price-chart :refer [stock-price-chart]]
    [money-1846.components.sidebar-menu :refer [sidebar-menu]]
    [money-1846.components.utils :refer [sidebar]]
    [money-1846.db :as db]
    [re-frame.core :as rf]))

(defn game-panel []
  (let [in-game-corporations (rf/subscribe [:in-game-corporations])]
    [:div.d-flex.flex-column {:style {:height "100vh"}}
     [stock-price-chart @in-game-corporations db/stock-chart-values]
     [:div.d-flex {:style {:flex 1}}
      [:div.w-40.border.bg-danger.h-100]
      [corporations-panel @in-game-corporations]]
     [:div.bg-secondary.bg-lighter
      [:div (pr-str @(rf/subscribe [:players]))]]]))

(defn main []
  [:div
   [sidebar sidebar-menu]
   [game-panel]])
