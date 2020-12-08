(ns a2020.day1
  (:require
    [clojure.test :refer :all]
    [clojure.java.io :as io]))

(defn part1 [expense-report]
  (first (for [x expense-report y expense-report :when (= 2020 (+ x y))] (* x y))))

(defn part2 [expense-report]
  (first (for [x expense-report y expense-report z expense-report :when (= 2020 (+ x y z))] (* x y z))))

(def example-input [1721
                    979
                    366
                    299
                    675
                    1456])

(def actual-input
  "Reads the report file into a vector converting each line to a Long"
  (with-open [reps (io/reader (io/resource "expense_report.txt"))]
    (map #(Long/parseLong %) (vec (line-seq reps)))))

(deftest test-day1
  (testing "Part 1 example"
    (is (= 514579 (part1 example-input))))
  (testing "Part 2 example"
    (is (= 241861950 (part2 example-input))))
  (testing "Part 1 actual"
    (is (= 787776 (part1 actual-input))))
  (testing "Part 2 actual"
    (is (= 262738554 (part2 actual-input)))))

(run-tests)
