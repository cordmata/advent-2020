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

(defn ski
  [x-slope y-slope terrain]
  (loop [x 0 y 0 trees-hit 0]
    (if (>= y (count terrain))
      trees-hit
      (let [row (nth terrain y) tree? (tree-at? row x)]
        (recur (+ x x-slope) (+ y y-slope) (+ trees-hit (if tree? 1 0)))))))

(defn part1 [input]
  (ski 3 1 (parse-terrain input)))

(defn part2 [input]
  (let [terrain (parse-terrain input)]
    (reduce * [(ski 1 1 terrain)
               (ski 3 1 terrain)
               (ski 5 1 terrain)
               (ski 7 1 terrain)
               (ski 1 2 terrain)])))

(def example-input "..##.......\n#...#...#..\n.#....#..#.\n..#.#...#.#\n.#...##..#.\n..#.##.....\n.#.#.#....#\n.#........#\n#.##...#...\n#...##....#\n.#..#...#.#")

(deftest test-day3
  (testing "Part 1 example"
    (is (= 7 (part1 example-input))))
  (testing "Part 1 actual"
    (is (= 164 (part1 actual-input))))
  (testing "Part 2 example"
    (is (= 336 (part2 example-input))))
  (testing "Part 2 actual"
    (is (= 5007658656 (part2 actual-input)))))

(run-tests)
