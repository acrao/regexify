(ns regexify.components
  (:require
    [reagent.core :as r]))

(def matches-state
  (r/atom "WTF"))

(defn regex-input
  []
  [:div#regex-input-div
   [:input#regex-input {:type "text" :name "clj regex"}]])

(defn match-text
  []
  [:div#match-text-div
   [:textarea#match-text {:rows 10 :placeholder "Match text goes here"}]])

(defn regex-matches
  [value]
  [:div#regex-matches-div
   [:span "Matches!"]
   [:br]
   [:span @value]])

(defn regexify-parent
  []
  [:div#app
   [:h1 "foobar"]
   [regex-input]
   [match-text]
   [regex-matches matches-state]])