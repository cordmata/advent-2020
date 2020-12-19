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

(defn in-range? [lower upper n]
  (and n (<= lower (Integer/parseInt n) upper)))

(defn check-height [h]
  (let [[_ val unit] (re-find #"^(\d+)(cm|in)$" (str h))]
    (case unit
      "cm" (in-range? 150 193 val)
      "in" (in-range? 59 76 val)
      false)))

(def passport-def
  "Set of field definitions [:fname required? validator]"
  #{[:byr true #(in-range? 1920 2002 %)]
    [:iyr true #(in-range? 2010 2020 %)]
    [:eyr true #(in-range? 2020 2030 %)]
    [:hgt true check-height]
    [:hcl true #(re-find #"^#([0-9]|[a-f]){6}$" (str %))]
    [:ecl true #(#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} %)]
    [:pid true #(re-find #"^(\d{9})$" (str %))]
    [:cid false any?]})

(defn- parse-passports [in]
  (for [record (str/split in #"\n\n")
        :let [fields (str/split record #"\s+")]]
    (into {} (for [f fields :let [[k v] (str/split f #":")]]
               [(keyword k) (str v)]))))

(defn day2 [passport]
  (->> passport-def
       (every?
        (fn [[id _ validate]] (validate (id passport))))))

(defn day1 [passport]
  (set/subset?
   (set (for [[name required?] passport-def :when required?] name))
   (set (keys passport))))

(defn count-valid [input strategy] (count (filter strategy (parse-passports input))))

(def invalid-passports "eyr:1972 cid:100
hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

iyr:2019
hcl:#602927 eyr:1967 hgt:170cm
ecl:grn pid:012533040 byr:1946

hcl:dab227 iyr:2012
ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

hgt:59cm ecl:zzz
eyr:2038 hcl:74454a iyr:2023
pid:3556412378 byr:2007")


(def valid-passports "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
hcl:#623a2f

eyr:2029 ecl:blu cid:129 byr:1989
iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

hcl:#888785
hgt:164cm byr:2001 iyr:2015 cid:88
pid:545766238 ecl:hzl
eyr:2022

iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719")

(def actual-input (slurp (io/resource "passports.txt")))

(parse-passports invalid-passports)
;; => ({:eyr "1972", :cid "100", :hcl "#18171d", :ecl "amb", :hgt "170", :pid "186cm", :iyr "2018", :byr "1926"}
;;     {:iyr "2019", :hcl "#602927", :eyr "1967", :hgt "170cm", :ecl "grn", :pid "012533040", :byr "1946"}
;;     {:hcl "dab227", :iyr "2012", :ecl "brn", :hgt "182cm", :pid "021572410", :eyr "2020", :byr "1992", :cid "277"}
;;     {:hgt "59cm", :ecl "zzz", :eyr "2038", :hcl "74454a", :iyr "2023", :pid "3556412378", :byr "2007"})

(deftest test-day4
  (are [strategy num in] (= num (count-valid in strategy))
    day1 2 example-input
    day1 260 actual-input
    day1 4 valid-passports
    day1 4 invalid-passports
    day2 4 valid-passports
    day2 0 invalid-passports
    day2 153 actual-input))

(run-tests)
