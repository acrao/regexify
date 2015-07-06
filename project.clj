(defproject regexify "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3297"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [prismatic/dommy "1.1.0"]
                 [reagent "0.5.0"]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-figwheel "0.3.5"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]

              :figwheel { :on-jsload "regexify.core/on-js-reload" }

              :compiler {:main regexify.core
                         :asset-path "js/compiled/out"
                         :output-to "resources/public/js/compiled/regexify.js"
                         :output-dir "resources/public/js/compiled/out"
                         :source-map-timestamp true }}
             {:id "min"
              :source-paths ["src"]
              :compiler {:output-to "resources/public/js/compiled/regexify.js"
                         :main regexify.core
                         :optimizations :advanced
                         :pretty-print false}}]}

  :figwheel {
             ;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             :nrepl-port 7888

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
})
