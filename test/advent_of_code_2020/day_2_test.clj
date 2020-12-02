(ns advent-of-code-2020.day-2-test
  (:require [advent-of-code-2020.day-2 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n"
               ["1-3 a: abcde"
                "1-3 b: cdefg"
                "2-9 c: ccccccccc"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 2 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 586 (solution-part-one problem-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 1 (solution-part-two example-input))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 352 (solution-part-two problem-input))))
