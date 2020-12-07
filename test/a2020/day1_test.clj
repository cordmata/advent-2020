(ns a2020.day1-test
  (:require [clojure.test :refer :all])
  (:require [a2020.day1 :refer :all]))

(def input [1721
            979
            366
            299
            675
            1456])

(deftest test-day1
  (testing "Part 1"
    (is (= 514579 (part1 input))))
  (testing "Part 2"
    (is (= 241861950 (part2 input)))))
