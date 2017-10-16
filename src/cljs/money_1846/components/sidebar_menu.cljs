(ns money-1846.components.sidebar-menu
  (:require
    [money-1846.components.utils :refer [clickaway collapsible-panel]]
    [reagent.core :as r]
    [re-frame.core :as rf]))

(defn add-remove-corporations []
  (let [corporations (rf/subscribe [:corporations])]
    [:ul.list-unstyled.px-4
     (for [[id {:keys [name in-game]}] @corporations]
       [:li {:key id}
        [:label.d-flex.justify-content-between.align-items-center
         [:div name]
         [:div.ml-5 [:input {:type :checkbox
                             :checked (boolean in-game)
                             :on-change #(if in-game
                                           (rf/dispatch-sync [:corporations/remove id])
                                           (rf/dispatch-sync [:corporations/add id]))}]]]])]))

(defn add-player []
  (let [input-value (r/atom "")]
    (fn []
      [clickaway {:on-clickaway #(reset! input-value "")}
       [:div.w-75.m-auto
        [:form.p-2 {:on-submit #(do (.preventDefault %)
                                    (rf/dispatch [:sidebar/close])
                                    (rf/dispatch [:player/add @input-value])
                                    (reset! input-value ""))}
         [:input.form-control {:type "text"
                               :value @input-value
                               :placeholder "Player name"
                               :on-change #(reset! input-value (-> % .-target .-value))}]]]])))

(defn sidebar-menu []
  (let [s (r/atom nil)]
    (fn []
      (let [open (rf/subscribe [:sidebar-open])]
        [clickaway {:on-clickaway #(when @open (rf/dispatch [:sidebar/close]))
                    :style {:transition "max-width 0.3s ease-out"
                            :overflow "hidden"
                            :max-width (if @open (:client-width @s) 0)
                            :z-index "100"}
                    :class "position-absolute h-100"}
         [:nav.bg-dark.h-100.p-4
          {:ref #(when (and % @open) (swap! s update :client-width (fnil identity (->> % (.-clientWidth) (* 1.5)))))}
          [:div.shadow.bg-lighter.rounded.p-1
           [:ul.list-unstyled.text-center
            [:li
             [collapsible-panel
              [:a.nav-link.text-center.text-light.h5 "Add Corporation"]
              [add-remove-corporations]]]
            [:li.px-4>hr.m-1]
            [:li
             [collapsible-panel
              [:a.nav-link.text-center.text-light "Add Player"]
              [add-player]]]]]]]))))
