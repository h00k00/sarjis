(defproject sarjis "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [cljs-react-material-ui "0.2.46"]
                 [reagent "0.6.0" :exclusions [cljsjs/react cljsjs/react-dom]]
                 [re-frame "0.9.4"]
                 [datascript "0.16.1"]
                 [cljs-ajax "0.5.9"]
                 [secretary "1.2.3"]]

  :plugins [[lein-cljsbuild "1.1.4"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/www/js" "target"]

  :figwheel {:css-dirs ["resources/www/css"] :http-server-root "www"}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.8.2"]]

    :plugins      [[lein-figwheel "0.5.9"]]
    }}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "sarjis.core/mount-root"}
     :compiler     {:main                 sarjis.core
                    :output-to            "resources/www/js/app.js"
                    :output-dir           "resources/www/js/out"
                    :asset-path           "js/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            sarjis.core
                    :output-to       "resources/www/js/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}


    ]}

  )
