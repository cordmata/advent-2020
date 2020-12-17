(ns a2020.day5
  (:require
   [clojure.test :refer :all]
   [clojure.string :as str]
   [clojure.java.io :as io]))

; bulid a seq of seqs representing the rows and seat ids
(def seats (for [row (range 128)] (map #(+ (* row 8) %) (range 8))))

(def example-input "FBFBBFFRLR\nBFFFBBFRRR\nFFFBBBFRRR\nBBFFBBFRLL")

(run-tests)
