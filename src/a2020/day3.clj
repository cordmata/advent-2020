(ns a2020.day3
  (:require
    [clojure.test :refer :all]
    [clojure.string :as str]
    [clojure.java.io :as io]))

(defn parse-terrain [in]
  "Converts the string representation into a 2d vec where the presence of trees are denoted by booleans"
  (mapv (fn [line] (mapv #(case % \# true false) line))
        (str/split-lines in)))

(defn tree-at? [coll idx]
  "Gets value at idx, if idx is larger than the coll it wraps around to the beginning."
  (nth coll (mod idx (count coll))))

(def actual-input (slurp (io/resource "tree_map.txt")))

(defn ski-attempt1
  ([terrain] (ski-attempt1 0 0 terrain 0))
  ([x y terrain trees-hit]
   (if (>= y (count terrain))
     trees-hit
     (let [row (nth terrain y) tree? (tree-at? row x)]
       (recur (+ x 3) (+ y 1) terrain (+ trees-hit (if tree? 1 0)))))))

(defn part1 [input]
    (ski-attempt1 (parse-terrain input)))

(def example-input "..##.......\n#...#...#..\n.#....#..#.\n..#.#...#.#\n.#...##..#.\n..#.##.....\n.#.#.#....#\n.#........#\n#.##...#...\n#...##....#\n.#..#...#.#")

(deftest test-day3
  (testing "Part 1 example"
    (is (= 7 (part1 example-input))))
  (testing "Part 1 actual"
    (is (= 164 (part1 actual-input)))))
;(testing "Part 2"
;  (is (= 1 (part2 input)))))

(run-tests)
