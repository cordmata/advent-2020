(ns a2020.day2
  (:require
    [clojure.java.io :as io]))

(defn parse-rule [rule]
  (apply assoc {}
         (interleave
           [:min :max :char :password]
           (rest (re-find (re-matcher #"(\d+)-(\d+) (.): (.*)" rule))))))

(def password_db
  (with-open [reps (io/reader (io/resource "password_db.txt"))]
    (map parse-rule (vec (line-seq reps)))))

(defn password-matches-requirements [password-rules]
  (let
    [occurrences (get (frequencies (:password password-rules)) (first (:char password-rules)) 0)]
    (and (>= occurrences (Integer/parseInt (:min password-rules)))
         (<= occurrences (Integer/parseInt (:max password-rules))))))

(defn part1 []
  (count (filter password-matches-requirements password_db)))
