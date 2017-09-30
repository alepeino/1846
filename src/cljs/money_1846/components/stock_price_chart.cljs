(ns money-1846.components.stock-price-chart
  (:require
    cljsjs.dragula
    [reagent.core :as r]
    [re-frame.core :as rf]))

(def dragula (.-dragula js/window))

(def token-margins {1 0
                    2 3
                    3 1.4
                    4 0.1
                    5 -0.6
                    6 -1.1
                    7 -1.4})

(defn corporation-stock-token [props id]
  [:div {:data-corporation-id id}
   [:img.rounded-circle.w-100.border.border-primary
    (r/merge-props props {:src (str "img/" (name id) ".svg")})]])

(defn stock-price-chart-segment [props value corporations]
  [:div.text-center.small.bg-warning.border
   (r/merge-props props {:class (if (<= 40 value 150) "font-weight-bold" "bg-lighter")
                         :style {:padding "0 0.1rem"}
                         :data-stock-value value})
   [:span.highlight-big value]
   (for [{id :id} (sort-by :price-last-updated corporations)]
     ^{:key id}
     [corporation-stock-token
      {:style {:margin-bottom (-> corporations count token-margins (str "vw"))}}
      id])])

(defn stock-price-chart [corporations chart-values]
  (let [drake (atom nil)
        prices (group-by #(or (:stock-price %) 0) (vals corporations))
        segment-width (/ 100 (count chart-values))
        dragula-options #js{:moves #(-> % .-dataset .-corporationId)
                            :revertOnSpill true}
        dragula-events #(doto % (.on "over" (fn [_ segment _] (-> segment (.-classList) (.add "highlight"))))
                                (.on "out" (fn [_ segment _] (-> segment (.-classList) (.remove "highlight"))))
                                (.on "drop" (fn [token segment source _]
                                              (.cancel @drake true)
                                              (when (not= (-> segment .-dataset .-stockValue) (-> source .-dataset .-stockValue))
                                                (rf/dispatch [:corporations/set-price
                                                              (-> token .-dataset .-corporationId keyword)
                                                              (-> segment .-dataset .-stockValue js/parseInt)])))))
        ref #(when % (-> % (.-children) (js/Array.from) (dragula dragula-options) ((partial reset! drake)) (dragula-events)))]
    [:div.d-flex.w-100 {:ref ref
                        :style {:height (str (* 4 segment-width) "vw")}}
     (for [value chart-values]
       ^{:key value}
       [stock-price-chart-segment
        {:style {:width (str segment-width "vw")}}
        value (prices value)])]))
