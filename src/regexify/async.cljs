(ns regexify.async
  (:require
    [cljs.core.async :refer [put! <! chan]]
    [dommy.core :as dommy])
  (:require-macros
    [dommy.core :refer [sel1]]
    [cljs.core.async.macros :refer [go go-loop]]))

;; regex
(def regex-chan (chan))

(defn regex-chan-handler
  []
  (go-loop []
    (let [])
    (recur)))

;; match-text
(def match-text-chan (chan))

;; matches
(def matches-chan (chan))



(defn keypress-event
  "Binds a key press event to a particular element with the given handler"
  [elem handler]
  (dommy/listen! (sel1 elem) :keypress handler))

(defn keypress-event-handler
  [chan type]
  (fn [e]
    (.log js/console "Event of type " type)
    (put! chan {:type type
                :target (.-selectedTarget e)})))

(defn async-events
  []
  (keypress-event :#regex-input
    (keypress-event-handler regex-chan :regex-input))
  (keypress-event :#match-text
    (keypress-event-handler regex-chan :match-input))
  (keypress-event :#regex-matches
    (keypress-event-handler regex-chan :regex-matches)))