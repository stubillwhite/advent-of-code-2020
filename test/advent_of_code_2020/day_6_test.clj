(ns advent-of-code-2020.day-6-test
  (:require [advent-of-code-2020.day-6 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n"
               ["abc"
                ""
                "a"
                "b"
                "c"
                ""
                "ab"
                "ac"
                ""
                "a"
                "a"
                "a"
                "a"
                ""
                "b"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 11 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 7120 (solution-part-one problem-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 6 (solution-part-two example-input))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 3570 (solution-part-two problem-input))))

