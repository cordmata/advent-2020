(ns a2020.day6
  (:require
    [clojure.test :refer :all]
    [clojure.string :as str]
    [clojure.java.io :as io]
    [clojure.set :as set]))

(defn parse-input [inpt]
  (for [group (str/split inpt #"\n\n")] (str/split-lines group)))

(defn part1 [inpt]
  (->> (parse-input inpt)
       (map #(reduce set/union (map set %)))
       (map count)
       (reduce +)))

(defn part2 [inpt]
  (->> (parse-input inpt)
       (map #(reduce set/intersection (map set %)))
       (map count)
       (reduce +)))

(def example-input "abc\n\na\nb\nc\n\nab\nac\n\na\na\na\na\n\nb\n")
(def actual-input (slurp (io/resource "customs_forms.txt")))

(deftest test-part1
  (is (= 11 (part1 example-input)))
  (is (= 6551 (part1 actual-input))))

(deftest test-part2
  (is (= 6 (part2 example-input)))
  (is (= 3358 (part2 actual-input))))

(run-tests)
