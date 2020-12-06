(ns advent-of-code-2020.day-1-test
  (:require [advent-of-code-2020.day-1 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n" [1721 979 366 299 675 1456]))

(deftest combinations-of-then-seq-of-possible-combinations
  (is (= [[]]                (combinations-of 0 [])))
  (is (= [[1]]               (combinations-of 1 [1])))
  (is (= [[1 2] [1 3] [2 3]] (combinations-of 2 [1 2 3]))))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 514579 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 145875 (solution-part-one problem-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 241861950 (solution-part-two example-input))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 69596112 (solution-part-two problem-input))))
