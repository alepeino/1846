(ns money-1846.components.utils
  (:require [reagent.core :as r]))

(defn props-children []
  ((juxt r/props r/children) (r/current-component)))
