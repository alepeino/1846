(ns money-1846.components.players-panel
  (:require
    [money-1846.components.portfolio :refer [portfolio cash]]))

(defn player-card [{:keys [id] :as player}]
  [:div.card.rounded.mb-4 {:class (str "bg-" (name id))}
   [:div.card-header.h6.text-center.px-5.text-light
    (:name player)]
   [:div.card-body.p-2.bg-lighter.d-flex
    [:div.w-75
     [portfolio (:portfolio player)]]
    [:div.w-25.ml-1.text-light
     [cash (:cash player)]]]
   [:div.card-footer.p-2.text-right.text-light.faint.small
    "Portfolio Total: $ " [:strong (:portfolio-total player)]]])

(defn players-panel [players]
  [:div.card-columns
   (for [[id player] players]
     ^{:key id}
     [player-card player])])
