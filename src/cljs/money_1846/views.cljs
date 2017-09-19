(ns money-1846.views
  (:require
    [money-1846.components.core :refer [sidebar collapsible-panel]]
    [reagent.core :as r]
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
                                (rf/dispatch-sync [:remove-corporation id])
                                (rf/dispatch-sync [:add-corporation id]))}]]])]))

(defn sidebar-menu []
  [:nav
   [:div.menu-section.bg-lighter.rounded.m-4.p-1
    [:ul.list-unstyled.text-center
     [:li
      [collapsible-panel {:class "nav-link text-center text-light h5"}
       "Add Corporation"
       [add-remove-corporations]]]
     [:li.px-4 [:hr.m-1]]
     [:li
      [collapsible-panel {:class "nav-link text-center text-light"}
       "Add Player"
       [:div "aaaa"]]]]]])

(defn main []
  (let [in-game-corporations (rf/subscribe [:in-game-corporations])]
    (fn []
      [:div
       [sidebar sidebar-menu]
       [:div.content (pr-str @in-game-corporations)]])))
