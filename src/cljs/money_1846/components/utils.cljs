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
         {:ref #(when % (->> % (.-clientHeight) (swap! s assoc :client-height)))}
         content]]])))
