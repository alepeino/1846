(ns money-1846.components.corporations-panel
  (:require
    [money-1846.components.utils :refer [props-children]]))

(defn share []
  (let [[{:keys [corporation-id] :as props} _] (props-children)]
    [:div.h-100.card.rounded.d-flex.flex-row.share
     (dissoc props :corporation-id)
     [:div.rounded-left {:class (str "bg-" (name corporation-id))}]
     [:div.rounded-right {:class (str "icon-" (name corporation-id))
                          :style {:flex 1}}
      [:h5.text-right.text-secondary.p-1.cursor-default "10%"]]]))

(defn corporation-card [{:keys [id] :as corporation}]
  [:div.card.rounded.mb-4 {:class (str "bg-" (name id))}
   [:div.card-header.h6.text-center.px-5
    {:class (str "icon-" (name id))}
    (:name corporation)]
   [:div.card-body.p-2.bg-lighter.d-flex
    (into [:div.corporation-portfolio.w-60]
          (for [n (-> corporation :portfolio id range)]
            [share {:corporation-id id
                    :class (str "stack-" n)}]))
    [:div.w-40.rounded.ml-1.shadow.d-flex.justify-content-center.align-items-center
     [:div (str "$ " (:cash corporation))]]]])

(defn corporations-panel [corporations]
  [:div.w-60.border.bg-info.h-100.card-columns.px-3.py-2
   (for [[id corporation] corporations]
     ^{:key id}
     [corporation-card corporation])])
