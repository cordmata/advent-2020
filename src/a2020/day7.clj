(ns a2020.day7
  (:require
   [clojure.test :refer :all]
   [clojure.string :as str]
   [clojure.java.io :as io]))

(defn keywordize [k] (keyword (str/replace k " " "_")))

(defn parse-bag-rule [rule]
  (let [[bag_name contents] (str/split rule  #" bags contain ")]
    {(keywordize bag_name)
     (->> contents
          (re-seq #"(\d) (\w+ \w+)")
          (map #(let [[_ count name] %] [(keywordize name) (Integer/parseInt count)]))
          (into {}))}))

(defn parse-bags [rules]
  (->>
   (str/split-lines rules)
   (map parse-bag-rule)
   (into {})))

(defn can-hold-color [bags bag-name color]
  (let [contents (bag-name bags)]
    (or
     (contains? contents color)
     (some #(can-hold-color bags % color) (keys contents)))))

(defn part1 [bags color]
  (count (filter #(can-hold-color bags % color) (keys bags))))

(defn count-contained-bags [bags bag-name]
  (->> (bag-name bags)
       (reduce
        (fn [cnt [bag num-bags]]
          (+ cnt num-bags (* num-bags (count-contained-bags bags bag)))), 0)))

(defn part2 [bags color]
  (count-contained-bags bags color))

(def example-input "light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.")

(comment
  (parse-bags example-input)
  ;; => {:bright_white {:shiny_gold 1},
  ;;     :dark_orange {:bright_white 3, :muted_yellow 4},
  ;;     :shiny_gold {:dark_olive 1, :vibrant_plum 2},
  ;;     :dotted_black {},
  ;;     :light_red {:bright_white 1, :muted_yellow 2},
  ;;     :dark_olive {:faded_blue 3, :dotted_black 4},
  ;;     :faded_blue {},
  ;;     :vibrant_plum {:faded_blue 5, :dotted_black 6},
  ;;     :muted_yellow {:shiny_gold 2, :faded_blue 9}}
  )

(def example-input-p2
  {:shiny_gold {:dark_red 2}
   :dark_red {:dark_orange 2}
   :dark_orange {:dark_yellow 2}
   :dark_yellow {:dark_green 2}
   :dark_green {:dark_blue 2}
   :dark_blue {:dark_violet 2}
   :dark_violet {}})

(def actual-input (slurp (io/resource "luggage_rules.txt")))

(deftest test-part1
  (are [f x y] (= x (f y :shiny_gold))
    part1 4 (parse-bags example-input)
    part1 370 (parse-bags actual-input)
    part2 32 (parse-bags example-input)
    part2 126 example-input-p2
    part2 370 (parse-bags actual-input)))

(run-tests)
