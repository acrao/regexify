(ns regexify.core
  (:require
    [regexify.components :as components]
    [regexify.async :as async]
    [cljs.core.async :refer [<!]]
    [reagent.core :as reagent :refer [atom]])
  (:require-macros
    [dommy.core :refer [sel1]]
    [cljs.core.async.macros :refer [go]]))

(defonce app-state (atom {:text "Hello world!"}))

(defn main
  []
  (enable-console-print!)
  (println "Edits to this text should show up in your developer console.")
  (reagent/render-component
    [components/regexify-parent]
    (sel1 :body))
  (async/async-events))

(defn on-js-reload
  []
  (swap! app-state update-in [:__figwheel_counter] inc))

(main)

(comment
  (use 'figwheel-sidecar.repl-api)
  (cljs-repl)
  (defn key-handler
    [e]
    (.log js/console "key press yo yo yo " (.-selectedTarget e)))

  (dommy/listen! (sel1 :#regex-input) :keypress key-handler))