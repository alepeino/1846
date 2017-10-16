(ns money-1846.views
  (:require
    [money-1846.components.corporations-panel :refer [corporations-panel]]
    [money-1846.components.stock-price-chart :refer [stock-price-chart]]
    [money-1846.components.sidebar-menu :refer [sidebar-menu]]
    [money-1846.db :as db]
    [re-frame.core :as rf]))

(defn game-panel []
  (let [in-game-corporations (rf/subscribe [:in-game-corporations])]
    [:div.d-flex.flex-column.flex-1
     [stock-price-chart @in-game-corporations db/stock-chart-values]
     [:div.d-flex.h-100
      [:div.w-60.border.bg-info.px-3.py-2
       [corporations-panel @in-game-corporations]]]]))

(defn status-bar []
  [:div.d-flex.justify-content-between.align-items-center.bg-secondary.bg-lighter
   [:div
    [:button.navbar-toggler.border-light.rounded-0.rounded-right
     {:on-click #(rf/dispatch-sync [:sidebar/toggle])}
     [:span.navbar-toggler-icon]]]
   [:div
    "status"]
   [:div
    "undo"]])

(defn main []
  [:div.d-flex.flex-column {:style {:height "100vh"}}
   [sidebar-menu]
   [game-panel]
   [status-bar]])
