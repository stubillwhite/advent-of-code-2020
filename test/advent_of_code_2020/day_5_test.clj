(ns advent-of-code-2020.day-5-test
  (:require [advent-of-code-2020.day-5 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n"
               ["FBFBBFFRLR"
                "BFFFBBFRRR"
                "FFFBBBFRRR"
                "BBFFBBFRLL"]))

(deftest seat-number-given-example-input-then-example-result
  (is (= 357 (seat-number "FBFBBFFRLR")))
  (is (= 567 (seat-number "BFFFBBFRRR")))
  (is (= 119 (seat-number "FFFBBBFRRR")))
  (is (= 820 (seat-number "BBFFBBFRLL"))))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 820 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-problem-result
  (is (= 915 (solution-part-one problem-input))))

(deftest solution-part-two-given-problem-input-then-problem-result
  (is (= 699 (solution-part-two problem-input))))
