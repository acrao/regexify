(ns regexify.regex)

(defn regex-matches
  [in-str txt]
  (try
    (re-find (re-pattern in-str) txt)
    (catch js/Error e
      (println "invalid regex : " in-str))))