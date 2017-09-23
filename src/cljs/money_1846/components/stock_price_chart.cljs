(ns money-1846.components.stock-price-chart
  (:require
    cljsjs.dragula
    [reagent.core :as r]
    [re-frame.core :as rf]))

(def dragula (.-dragula js/window))

(defn corporation-stock-token [props id]
  [:div
   [:img.rounded-circle.w-100.border.border-primary
    (r/merge-props props {:src (str "img/" (name id) ".svg")})]])

(defn stock-price-chart-segment [props value corporations]
  [:div.text-center.small.bg-warning.border
   (r/merge-props props {:class (if (<= 40 value 150) "font-weight-bold" "bg-lighter")
                         :style {:padding "0 0.1rem"}})
   value
   (for [{id :id} corporations]
     ^{:key id}
     [corporation-stock-token
      {:style {:margin-bottom (-> 3.2 (- (* 0.6 (count corporations))) (str "vw"))}}
      id])])

(defn stock-price-chart [corporations chart-values]
  (let [prices (group-by #(or (:stock-price %) 0) (vals corporations))
        segment-width (/ 100 (count chart-values))
        dragula-options #js{}
        ref #(when % (-> % (.-children) (js/Array.from) (dragula dragula-options)))]
    [:div.d-flex {:ref ref
                  :style {:height (str (* 5 segment-width) "vw")}}
     (for [value chart-values]
       ^{:key value}
       [stock-price-chart-segment
        {:style {:width (str segment-width "vw")}}
        value (prices value)])]))
