(ns a2020.day2-test
  (:require [clojure.test :refer :all])
  (:require [a2020.day2 :refer :all]))

(def input (map parse-rule ["1-3 a: abcde"
                            "1-3 b: cdefg"
                            "2-9 c: ccccccccc"]))

(deftest test-day2
  (testing "Part 1"
    (is (= 2 (part1 input))))
  (testing "Part 2"
    (is (= 1 (part2 input)))))
