(ns money-1846.components.corporations-panel
  (:require
    [money-1846.components.portfolio :refer [portfolio cash]]))

(defn corporation-card [{:keys [id] :as corporation}]
  [:div.card.rounded.mb-4 {:class (str "bg-" (name id))}
   [:div.card-header.h6.text-center
    {:class (str "icon-" (name id))
     :title (:name corporation)}
    [:span.d-none.d-xl-block.px-4 (:name corporation)]
    [:span.d-block.d-xl-none (:short-name corporation)]]
   [:div.card-body.p-2.bg-lighter.d-flex
    [:div.w-60
     [portfolio (:portfolio corporation)]]
    [:div.w-40.ml-1.h6.m-0
     [cash (:cash corporation)]]]])

(defn corporations-panel [corporations]
  [:div.card-columns
   (for [[id corporation] corporations]
     ^{:key id}
     [corporation-card corporation])])
