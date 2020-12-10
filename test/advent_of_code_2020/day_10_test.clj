(ns advent-of-code-2020.day-10-test
  (:require [advent-of-code-2020.day-10 :refer :all]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input-one
  (string/join "\n"
               [16
                10
                15
                5
                1
                11
                7
                19
                6
                12
                4]))

(def example-input-two
  (string/join "\n"
               [28
                33
                18
                42
                31
                14
                46
                20
                48
                47
                24
                23
                49
                45
                19
                38
                39
                11
                1
                32
                25
                35
                8
                17
                7
                9
                4
                2
                34
                10
                3]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 35  (solution-part-one example-input-one)))
  (is (= 220 (solution-part-one example-input-two))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 2376 (solution-part-one problem-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 8     (solution-part-two example-input-one)))
  (is (= 19208 (solution-part-two example-input-two))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 129586085429248 (solution-part-two problem-input))))
