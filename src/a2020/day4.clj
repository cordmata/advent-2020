(ns a2020.day4
  (:require
    [clojure.test :refer :all]
    [clojure.string :as str]
    [clojure.java.io :as io]
    [clojure.set :as set]))

; Required fields
;
; byr (Birth Year)
; iyr (Issue Year)
; eyr (Expiration Year)
; hgt (Height)
; hcl (Hair Color)
; ecl (Eye Color)
; pid (Passport ID)
; cid (Country ID) -- this one to be hacked (missing ok)

(def example-input "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm\n\niyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\nhcl:#cfa07d byr:1929\n\nhcl:#ae17e1 iyr:2013\neyr:2024\necl:brn pid:760753108 byr:1931\nhgt:179cm\n\nhcl:#cfa07d eyr:2025 pid:166559648\niyr:2011 ecl:brn hgt:59in")

(def passport-def
  "Set of field definitions [:fname required?]"
  #{[:byr true]
    [:iyr true]
    [:eyr true]
    [:hgt true]
    [:hcl true]
    [:ecl true]
    [:pid true]
    [:cid false]})

(defn- parse-passports [in]
  (for [record (str/split in #"\n\n")
        :let [fields (str/split record #"\s+")]]
    (into {} (map #(str/split % #":") fields))))

(defn- is-valid? [passport]
  (set/subset?
    (set (for [[name required?] passport-def :when required?] name))
    (set (map keyword (keys passport)))))

(defn part1 [input] (count (filter is-valid? (parse-passports input))))

(def actual-input (slurp (io/resource "passports.txt")))

(deftest test-day4
  (testing "Part 1 example"
    (is (= 2 (part1 example-input))))
  (testing "Part 1 actual"
    (is (= 260 (part1 actual-input)))))

(run-tests)
