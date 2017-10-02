(ns money-1846.components.sidebar-menu
  (:require
    [money-1846.components.utils :refer [collapsible-panel]]
    [re-frame.core :as rf]))

(defn add-remove-corporations []
  (let [corporations (rf/subscribe [:corporations])]
    [:ul.list-unstyled.px-4
     (for [[id {:keys [name in-game]}] @corporations]
       [:li {:key id}
        [:label.d-flex.justify-content-between.align-items-center
         name
         [:input {:type :checkbox
                  :checked (boolean in-game)
                  :on-change #(if in-game
                                (rf/dispatch-sync [:corporations/remove id])
                                (rf/dispatch-sync [:corporations/add id]))}]]])]))

(defn sidebar-menu []
  [:nav
   [:div.shadow.bg-lighter.rounded.m-4.p-1
    [:ul.list-unstyled.text-center
     [:li
      [collapsible-panel
       [:a.nav-link.text-center.text-light.h5 "Add Corporation"]
       [add-remove-corporations]]]
     [:li.px-4>hr.m-1]
     [:li
      [collapsible-panel
       [:a.nav-link.text-center.text-light "Add Player"]
       [:div ""]]]]]])
