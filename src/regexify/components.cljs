(ns regexify.components)

(defn regex-input
  []
  [:div#regex-input-div
   [:input#regex-input {:type "text" :name "clj regex"}]])

(defn match-text
  []
  [:div#match-text-div
   [:textarea#match-text {:rows 10 :placeholder "Match text goes here"}]])


(defn regex-matches
  []
  [:div#regex-matches-div
   [:textarea#regex-matches {:rows 10 :placeholder "Matches!"}]])

(defn regexify-parent
  []
  [:div#app
   [:h1 "foobar"]
   [regex-input]
   [match-text]
   [regex-matches]])