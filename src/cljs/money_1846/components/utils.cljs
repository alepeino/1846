(ns money-1846.components.utils
  (:require [reagent.core :as r]))

(defn props-children []
  ((juxt r/props r/children) (r/current-component)))

(defn clickaway []
  (let [[props children] (props-children)]
    (r/with-let [root (r/atom nil)
                 on-clickaway #(when (and @root (not (.contains @root (.-target %))))
                                 ((:on-clickaway props)))
                 _ (js/document.addEventListener "mousedown" on-clickaway)]
                (into [:div (merge (dissoc props :on-clickaway)
                                   {:ref (partial reset! root)})]
                      children)
                (finally (js/document.removeEventListener "mousedown" on-clickaway)))))

(defn sidebar [content]
  (let [open (r/atom false)]
    (fn []
      [clickaway {:id "sidebar"
                  :on-clickaway #(reset! open false)}
       [:nav.p-1.bg-danger
        [:button.navbar-toggler.align-self-start
         {:type "button"
          :on-click #(swap! open not)}
         [:span.navbar-toggler-icon.small]]]
       [:div.sidebar-content.w-25.bg-info {:class (when @open "open")}
        [content]]])))

(defn collapsible-panel [title content]
  (let [default {:open false}
        s (r/atom default)]
    (fn []
      [clickaway {:on-clickaway #(when (:open @s) (reset! s default))}
       [:div.cursor-default
        {:on-click #(swap! s update :open not)}
        title]
       [:div {:style {:transition "max-height 0.3s ease-out"
                      :overflow "hidden"
                      :max-height (if (:open @s) (:client-height @s) 0)}}
        [:div
         {:ref #(when % (->> % (.-clientHeight) (swap! s assoc :client-height)))
          :style {:display "inline-block"
                  :width "100%"}}
         content]]])))
