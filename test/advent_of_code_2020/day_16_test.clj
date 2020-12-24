(ns advent-of-code-2020.day-16-test
  (:require [advent-of-code-2020.day-16 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input
  (string/join "\n"
               ["class: 1-3 or 5-7"
                "row: 6-11 or 33-44"
                "seat: 13-40 or 45-50"
                ""
                "your ticket:"
                "7,1,14"
                ""
                "nearby tickets:"
                "7,3,47"
                "40,4,50"
                "55,2,20"
                "38,6,12"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 71 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 21978 (solution-part-one problem-input))))
