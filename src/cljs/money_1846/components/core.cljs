(ns money-1846.components.core
  (:require [reagent.core :as r]))

(defn sidebar [content]
  (let [open (r/atom false)]
    (fn []
      [:div#sidebar
       [:nav.p-1.bg-danger
        [:button.navbar-toggler.align-self-start
         {:type "button"
          :on-click #(swap! open not)}
         [:span.navbar-toggler-icon.small]]]
       [:div.sidebar-content.w-50.bg-info {:class (when @open "open")}
        [content]]])))

(defn collapsible-panel [title-attrs title content]
  (let [default {:open false}
        s (r/atom default)
        clickaway #(if (and (:open @s)
                            (:root @s)
                            (not (.contains (:root @s) (.-target %))))
                     (reset! s default))]
    (r/create-class
      {:component-did-mount #(.addEventListener js/document "mousedown" clickaway)
       :component-will-unmount #(.removeEventListener js/document "mousedown" clickaway)
       :reagent-render
       (fn []
         [:div {:ref #(swap! s assoc :root %)}
          [:a (merge
                {:on-click #(swap! s update :open not)}
                title-attrs)
           title]
          [:div {:style {:transition "max-height 0.3s ease-out"
                         :overflow "hidden"
                         :max-height (if (:open @s) (:client-height @s) 0)}}
           [:div
            {:ref #(when % (->> % .-clientHeight (swap! s assoc :client-height)))
             :style {:display "inline-block"
                     :width "100%"}}
            content]]])})))
