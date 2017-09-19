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
                                (rf/dispatch-sync [:remove-corporation id])
                                (rf/dispatch-sync [:add-corporation id]))}]]])]))

(defn corporation-stock-token [id]
  [:img.rounded-circle.w-100 {:src (str "img/" (name id) "-circle.png")
                              :draggable true
                              :on-drag-start #(.setData (.-dataTransfer %) "text/plain" "")}])

(defn stock-scale [scale]
  (let [corporations (rf/subscribe [:in-game-corporations])
        prices (group-by :stock-price (vals @corporations))]
    [:div.d-flex
     {:style {:height "10rem"}}
     (for [value scale]
       ^{:key value}
       [:div.text-center.small.bg-warning.border
        {:style {:width (str (/ 100 (count scale)) "%")}
         :class (if (<= 40 value 150) "font-weight-bold" "bg-lighter")}
        [:div.mb-1 value]
        [:div
         (for [{id :id} (prices value)]
           ^{:key id}
           [corporation-stock-token id])]])]))
