(ns money-1846.views
  (:require
    [money-1846.components.core :as components]
    [money-1846.components.utils :refer [props-children]]
    [money-1846.db :as db]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn sidebar-menu []
  [:nav
   [:div.shadow.bg-lighter.rounded.m-4.p-1
    [:ul.list-unstyled.text-center
     [:li
      [components/collapsible-panel
       [:a.nav-link.text-center.text-light.h5 "Add Corporation"]
       [components/add-remove-corporations]]]
     [:li.px-4>hr.m-1]
     [:li
      [components/collapsible-panel
       [:a.nav-link.text-center.text-light "Add Player"]
       [:div ""]]]]]])

(defn share []
  (let [[{:keys [corporation-id] :as props} _] (props-children)]
    [:div.h-100.card.rounded.d-flex.flex-row.share
     (dissoc props :corporation-id)
     [:div.rounded-left.w-10 {:class (str "bg-" (name corporation-id))}]
     [:div.rounded-right {:class (str "icon-" (name corporation-id))
                          :style {:flex 1}}
      [:h2.text-right.text-dark.p-1.cursor-default "10%"]]]))

(defn corporation-card [{:keys [id] :as corporation}]
  [:div.card.rounded.mb-4 {:class (str "bg-" (name id))}
   [:div.card-header.h6.text-center.px-5
    {:class (str "icon-" (name id))}
    (:name corporation)]
   [:div.card-body.p-2.bg-lighter
    (into [:div.corporation-portfolio]
     (for [n (-> corporation :portfolio id range)]
       [share {:corporation-id id
               :class (str "stack-" n)}]))]])

(defn corporations-panel [corporations]
  [:div.w-60.border.bg-info.h-100.card-columns.px-3.py-2
   (for [[id corporation] corporations]
     ^{:key id}
     [corporation-card corporation])])

(defn game-panel [in-game-corporations]
  (let [in-game-corporations (rf/subscribe [:in-game-corporations])]
    [:div.d-flex.flex-column {:style {:height "100vh"}}
     [components/stock-price-chart @in-game-corporations db/stock-chart-values]
     [:div.d-flex {:style {:flex 1}}
      [:div.w-40.border.bg-danger.h-100]
      [corporations-panel @in-game-corporations]]
     [:div.bg-secondary.bg-lighter
      "status"]]))

(defn main []
  [:div
   [components/sidebar sidebar-menu]
   [game-panel]])
