(ns regexify.core
  (:require
    [regexify.components :as components]
    [regexify.async :as async]
    [cljs.core.async :refer [<! chan]]
    [reagent.core :as reagent :refer [atom]])
  (:require-macros
    [dommy.core :refer [sel1]]
    [cljs.core.async.macros :refer [go go-loop]]))

(defonce app-state (atom {:text "Hello world!"}))

(defn flow
  []
  (let [input-chan (chan)
        matches-chan (chan)]
    (async/bind-input-events input-chan)
    (async/input-listner input-chan matches-chan)
    (async/output-listner matches-chan #(.log js/console %))))

(defn main
  []
  (enable-console-print!)
  (println "Edits to this text should show up in your developer console.")
  (reagent/render-component
    [components/regexify-parent]
    (sel1 :body))
  (flow))

(defn on-js-reload
  []
  (swap! app-state update-in [:__figwheel_counter] inc))

(main)

;(comment
;  (use 'figwheel-sidecar.repl-api)
;  (cljs-repl)
;  (defn key-handler
;    [e]
;    (.log js/console "key press yo yo yo " (.-selectedTarget e)))
;
;  (dommy/listen! (sel1 :#regex-input) :keypress key-handler))