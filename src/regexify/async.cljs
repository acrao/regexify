(ns regexify.async
  (:require
    [cljs.pprint :refer [pprint]]
    [cljs.core.async :refer [put! <! chan]]
    [regexify.components :refer [matches-state]]
    [dommy.core :as dommy])
  (:require-macros
    [dommy.core :refer [sel1]]
    [cljs.core.async.macros :refer [go go-loop]]))

(defn- elem-keyword->class
  [elem-keyword]
  (->> elem-keyword name (str "#") keyword))

(defn- echo-handler
  [chan type]
  (fn [e]
    (put! chan {:type type
              :target (.-target e)})))

(defn bind-echo-keypress-event
  "Binds a key press event to a particular element with the given handler"
  [chan elem]
  (dommy/listen! (-> elem elem-keyword->class sel1)
    :keypress (echo-handler chan elem)))

(defn bind-input-events
  [chan]
  (bind-echo-keypress-event chan :regex-input)
  (bind-echo-keypress-event chan :match-text))

(defn input-listner
  [input-chan out-chan]
  (go-loop []
    (let [{:keys [type target]} (<! input-chan)]
      (.log js/console (.-value target))
      (put! out-chan (str type " : " (.-value target))))
    (recur)))

(defn- update-matches-state
  [matches]
  (println "updating matches : " matches)
  (reset! matches-state matches)
  (println "new match state : " @matches-state))

(defn output-listner
  [out-chan handler]
  (println "starting output listener")
  (go-loop []
    (update-matches-state (<! out-chan))
    (recur)))