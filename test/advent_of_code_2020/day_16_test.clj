(ns advent-of-code-2020.day-16-test
  (:require [advent-of-code-2020.day-16 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
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

(def- small-input
  (string/join "\n"
               ["class: 0-1 or 4-19"
                "row: 0-5 or 8-19"
                "seat: 0-13 or 16-19"
                ""
                "your ticket:"
                "11,12,13"
                ""
                "nearby tickets:"
                "3,9,18"
                "15,1,5"
                "5,14,9"]))

(deftest find-fields-which-fit-ticket-values-given-small-input-then-potential-matches
  (let [parsed-input (parse-and-categorise-tickets small-input)
        expected     {0 #{:row}
                      1 #{:class :row}
                      2 #{:class :row :seat}}]
    (is (= expected (find-fields-which-fit-ticket-values parsed-input)))))

(deftest resolve-fields-given-solvable-fields-then-resolves
  (let [fields-by-idx {0 #{:foo :qux} 1 #{:bar :foo :qux} 2 #{:qux}}]
    (is (= [:foo :bar :qux] (resolve-fields fields-by-idx)))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 1053686852011 (solution-part-two problem-input))))

