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

(defn regex-matches
  [in-str txt]
  (try
    (re-find (re-pattern in-str) txt)
    (catch js/Error e
      (println "invalid regex : " in-str))))

(defn regex-groups
  [in-str txt]
  (drop 1 (re-groups (re-matcher (re-pattern in-str) txt)))

  #_(try
    (drop 1 (re-groups (re-matcher (re-pattern in-str) txt)))
    (catch js/Error e
      (println "unable to get re groups"))))

(defn- update-matches-state
  [{:keys [type target]}]
  (let [regex-input (if (= type :regex-input)
                      target
                      (-> (sel1 :#regex-input) (.-value)))
        match-text (if (= type :match-text)
                       target
                       (-> (sel1 :#match-text) (.-value)))]
    (println "Resolved regex-input " regex-input " match-text " match-text)
    (when (and regex-input match-text)
      (swap! matches-state merge {:matches (regex-matches regex-input match-text)
                                  :groups (regex-groups regex-input match-text)}))))

(defn output-listner
  [out-chan handler]
  (go-loop []
    (update-matches-state (<! out-chan))
    (recur)))