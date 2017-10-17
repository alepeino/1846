(ns money-1846.views
  (:require
    [money-1846.components.corporations-panel :refer [corporations-panel]]
    [money-1846.components.players-panel :refer [players-panel]]
    [money-1846.components.portfolio :refer [cash]]
    [money-1846.components.stock-price-chart :refer [stock-price-chart]]
    [money-1846.components.sidebar-menu :refer [sidebar-menu]]
    [money-1846.db :as db]
    [re-frame.core :as rf]))

(defn bank-panel [bank-money]
  [:div.bg-lighter.text-light.rounded
   [:div.d-flex.p-2
    [:div.w-60.p-2
     [:h3.m-0.text-muted "Bank / Market"]]
    [:div.w-40.h4.m-0
     [cash bank-money]]]])

(defn game-panel []
  (let [bank-money (rf/subscribe [:bank])
        in-game-corporations (rf/subscribe [:in-game-corporations])
        players (rf/subscribe [:players-computed-portfolio])]
    [:div.d-flex.flex-column.flex-1
     [stock-price-chart @in-game-corporations db/stock-chart-values]
     [:div.d-flex.h-100
      [:div.w-60.bg-info.border.d-flex
       [:div.p-2 {:style {:overflow-y :auto}}
        [players-panel @players]]]
      [:div.w-40.bg-secondary.border.d-flex.flex-column
       [:div.p-2
        [bank-panel @bank-money]]
       [:div.px-3.py-2.flex-1 {:style {:overflow-y :auto}}
        [corporations-panel @in-game-corporations]]]]]))

(defn status-bar []
  [:div.d-flex.justify-content-between.align-items-center.bg-secondary.bg-lighter
   [:div
    [:button.navbar-toggler.border-light.rounded-0.rounded-right
     {:on-click #(rf/dispatch [:sidebar/toggle])}
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
