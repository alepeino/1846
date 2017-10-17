(ns money-1846.components.portfolio
  (:require
    [money-1846.components.utils :refer [props-children]]))

(defn share []
  (let [[{:keys [corporation-id] :as props} _] (props-children)]
    [:div.h-100.card.rounded.d-flex.flex-row.share
     (dissoc props :corporation-id)
     [:div.rounded-left {:class (str "bg-" (name corporation-id))}]
     [:div.rounded-right.flex-1 {:class (str "icon-" (name corporation-id))}
      [:h6.text-right.text-secondary.p-1.cursor-default "10%"]]]))

(defn portfolio [pf]
  (into [:div.rounded.shadow.portfolio {:style {:min-height "3rem"}}]
    (for [[id amount] pf]
      (into [:div.mb-2.share-stack {:title (str amount " shares")}]
        (for [n (range amount)]
          [share {:corporation-id id
                  :class (str "stack-" n)}])))))

(defn cash [amount]
  [:div.rounded.shadow.d-flex.justify-content-between.align-items-center.h-100.p-2
   [:div.m-0 "$ "]
   [:div.m-0 amount]])
