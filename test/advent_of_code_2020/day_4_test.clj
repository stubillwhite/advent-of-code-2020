(ns advent-of-code-2020.day-4-test
  (:require [advent-of-code-2020.day-4 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n"
               ["ecl:gry pid:860033327 eyr:2020 hcl:#fffffd"
                "byr:1937 iyr:2017 cid:147 hgt:183cm"
                ""
                "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884"
                "hcl:#cfa07d byr:1929"
                ""
                "hcl:#ae17e1 iyr:2013"
                "eyr:2024"
                "ecl:brn pid:760753108 byr:1931"
                "hgt:179cm"
                ""
                "hcl:#cfa07d eyr:2025 pid:166559648"
                "iyr:2011 ecl:brn hgt:59in"]))

(def- parsed-example-input
  [{"ecl" "gry"
    "pid" "860033327"
    "eyr" "2020"
    "hcl" "#fffffd"
    "byr" "1937"
    "iyr" "2017"
    "cid" "147"
    "hgt" "183cm"}
   
   {"iyr" "2013"
    "ecl" "amb"
    "cid" "350"
    "eyr" "2023"
    "pid" "028048884"
    "hcl" "#cfa07d"
    "byr" "1929"}
   
   {"hcl" "#ae17e1"
    "iyr" "2013"
    "eyr" "2024"
    "ecl" "brn"
    "pid" "760753108"
    "byr" "1931"
    "hgt" "179cm"}
   
   {"hcl" "#cfa07d"
    "eyr" "2025"
    "pid" "166559648"
    "iyr" "2011"
    "ecl" "brn"
    "hgt" "59in"}])

(deftest parse-input-given-example-input-then-example-output
  (is (= parsed-example-input (parse-input example-input))))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 2 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 196 (solution-part-one problem-input))))
