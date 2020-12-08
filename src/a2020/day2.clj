(ns a2020.day2
  (:require
    [clojure.test :refer :all]
    [clojure.java.io :as io]))

(defn parse-rule [rule]
  (let [[_ min max char password] (re-find #"(\d+)-(\d+) (.): (.*)" rule)]
    [(Integer/parseInt min) (Integer/parseInt max) (first char) password]))

(def actual-password-db
  (with-open [reps (io/reader (io/resource "password_db.txt"))]
    (map parse-rule (vec (line-seq reps)))))

(defn password-matches-initial-requirements [[min, max, char, password]]
  (let [occurrences (get (frequencies password) char 0)]
    (and (>= occurrences min) (<= occurrences max))))

(defn password-matches-updated-requirements [[pos1, pos2, char, password]]
  (let [val1 (nth password (dec pos1) "")
        val2 (nth password (dec pos2) "")]
    (and (some #(= char %) [val1 val2]) (not= val1 val2)))) ;; ugh, I really don't like this but it works

(defn part1 [password-db]
  (count (filter password-matches-initial-requirements password-db)))

(defn part2 [password-db]
  (count (filter password-matches-updated-requirements password-db)))

(def input (map parse-rule ["1-3 a: abcde"
                            "1-3 b: cdefg"
                            "2-9 c: ccccccccc"]))

(deftest test-day2
  (testing "Part 1 example"
    (is (= 2 (part1 input))))
  (testing "Part 2 example"
    (is (= 1 (part2 input))))
  (testing "Part 1 actual"
    (is (= 517 (part1 actual-password-db))))
  (testing "Part 2 actual"
    (is (= 284 (part2 actual-password-db)))))

(run-tests)
