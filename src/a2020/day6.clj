(ns a2020.day6
  (:require
    [clojure.test :refer :all]
    [clojure.string :as str]
    [clojure.java.io :as io]
    [clojure.set :as set]))

(def group-example  ["abcx" "abcy" "abcz"])
(def example-input "abc\n\na\nb\nc\n\nab\nac\n\na\na\na\na\n\nb\n")

(defn find-yes-answers [group-answers] (reduce set/union (map set group-answers)))

(defn parse-input [inpt]
  (for [group (str/split inpt #"\n\n")] (str/split-lines group)))

(defn part1 [inpt]
  (->> inpt
       parse-input
       (map find-yes-answers)
       (map count)
       (reduce +)))

(deftest test-count-yeses
  (is (= 6 (count (find-yes-answers group-example)))))

(deftest test-part1-example
  (is (= 11 (part1 example-input)))
  (is (= 6551 (part1 (slurp (io/resource "customs_forms.txt"))))))

(run-tests)
