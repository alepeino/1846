(defproject money-1846 "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.908"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.1"]
                 [day8.re-frame/undo "0.3.2"]]

  :plugins [[lein-cljsbuild "1.1.5"]]

  :min-lein-version "2.5.3"

  :figwheel {:css-dirs ["resources/public/css"]}

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} [:target-path
                                    [:cljsbuild :builds :dev :compiler :output-dir]
                                    [:cljsbuild :builds :prod :compiler :output-to]
                                    [:figwheel :css-dirs]]

  :profiles
  {:dev
   {:plugins      [[lein-figwheel "0.5.13"]
                   [deraen/lein-sass4clj "0.3.1"]]
    :dependencies [[binaryage/devtools "0.9.4"]
                   [figwheel-sidecar "0.5.13"]
                   [org.slf4j/slf4j-nop "1.7.13"]
                   [org.webjars.bower/bootstrap "4.0.0-beta" :exclusions [org.webjars.bower/jquery
                                                                          org.webjars.bower/popper.js]]]
    :sass         {:source-paths ["resources/sass/"]
                   :target-path "resources/public/css"
                   :output-style :expanded
                   :source-map true}}}

  :cljsbuild
  {:builds {:dev {:source-paths ["src/cljs"]
                  :figwheel     {:on-jsload "money-1846.core/mount-root"}
                  :compiler     {:main                 money-1846.core
                                 :output-to            "resources/public/js/app.js"
                                 :output-dir           "resources/public/js/out"
                                 :asset-path           "js/out"
                                 :source-map-timestamp true
                                 :preloads             [devtools.preload]
                                 :external-config      {:devtools/config {:features-to-install :all}}}}

            :prod {:source-paths ["src/cljs"]
                   :compiler     {:main            money-1846.core
                                  :output-to       "resources/public/js/app.js"
                                  :optimizations   :advanced
                                  :closure-defines {goog.DEBUG false}
                                  :pretty-print    false}}}})
