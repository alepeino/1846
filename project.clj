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
   {:dependencies [[binaryage/devtools "0.9.4"]
                   [figwheel-sidecar "0.5.13"]]
    :plugins      [[lein-figwheel "0.5.13"]
                   [lein-npm "0.6.2"]]
    :npm {:devDependencies [[laravel-mix "1.4.3"]
                            [bootstrap "github:twbs/bootstrap#v4-dev"]]
          :package {:scripts {:watch "NODE_ENV=development node_modules/webpack/bin/webpack.js --watch --progress --hide-modules --config=node_modules/laravel-mix/setup/webpack.config.js"
                              :production "NODE_ENV=production node_modules/webpack/bin/webpack.js --progress --hide-modules --config=node_modules/laravel-mix/setup/webpack.config.js"}}}}}

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
