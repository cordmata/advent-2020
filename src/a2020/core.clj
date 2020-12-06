(ns a2020.core
  (:require [a2020.day1])
  (:gen-class))

(defn print-day1 []
  (println (str "Part 1: " (a2020.day1/part1)))
  (println (str "Part 2: " (a2020.day1/part2))))

(defn -main
  []
  (print-day1))
