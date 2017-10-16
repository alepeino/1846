(ns money-1846.components.portfolio
  (:require
    [money-1846.components.utils :refer [props-children]]))

(defn share []
  (let [[{:keys [corporation-id] :as props} _] (props-children)]
    [:div.h-100.card.rounded.d-flex.flex-row.share
     (dissoc props :corporation-id)
     [:div.rounded-left {:class (str "bg-" (name corporation-id))}]
     [:div.rounded-right.flex-1 {:class (str "icon-" (name corporation-id))}
      [:h5.text-right.text-secondary.p-1.cursor-default "10%"]]]))

(defn portfolio [pf]
  (into [:div.portfolio]
    (for [[id amount] pf]
      (into [:div.share-stack]
        (for [n (range amount)]
          [share {:corporation-id id
                  :class (str "stack-" n)}])))))
