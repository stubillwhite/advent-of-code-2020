(ns advent-of-code-2020.day-9-test
  (:require [advent-of-code-2020.day-9 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n"
               [35
                20
                15
                25
                47
                40
                62
                55
                65
                95
                102
                117
                150
                182
                127
                219
                299
                277
                309
                576]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 127 (solution-part-one example-input 5))))

(deftest solution-part-one-given-problem-input-then-problem-result
  (is (= 85848519 (solution-part-one problem-input 25))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 62 (solution-part-two example-input 5))))

(deftest solution-part-two-given-problem-input-then-problem-result
  (is (= 13414198 (solution-part-two problem-input 25))))
