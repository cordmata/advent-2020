(ns a2020.day1
  (:require [clojure.java.io :as io]))

(def expense-report
  "Reads the report file into a vector converting each line to a Long"
  (with-open [reps (io/reader (io/resource "expense_report.txt"))]
    (map #(Long/parseLong %) (vec (line-seq reps)))))

(defn part1 []
  (first (for [x expense-report y expense-report :when (= 2020 (+ x y))] (* x y))))

(defn part2 []
  (first (for [x expense-report y expense-report z expense-report :when (= 2020 (+ x y z))] (* x y z))))
