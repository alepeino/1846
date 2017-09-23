(ns money-1846.components.game-components
  (:require
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
