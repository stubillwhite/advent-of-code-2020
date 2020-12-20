(ns advent-of-code-2020.day-14-test
  (:require [advent-of-code-2020.day-14 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input-one
  (string/join "\n"
   ["mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"
    "mem[8] = 11"
    "mem[7] = 101"
    "mem[8] = 0"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 165 (solution-part-one example-input-one))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 14954914379452 (solution-part-one problem-input))))

(def- example-input-two
  (string/join "\n"
   ["mask = 000000000000000000000000000000X1001X"
    "mem[42] = 100"
    "mask = 00000000000000000000000000000000X0XX"
    "mem[26] = 1"]))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 208 (solution-part-two example-input-two))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 3415488160714 (solution-part-two problem-input))))
