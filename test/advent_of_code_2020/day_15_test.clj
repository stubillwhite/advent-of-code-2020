(ns advent-of-code-2020.day-15-test
  (:require [advent-of-code-2020.day-15 :refer :all]
            [clojure.test :refer :all]))

(deftest solution-part-one-given-example-inputs-then-example-results
  (is (= 436  (solution-part-one "0,3,6")))
  (is (= 1    (solution-part-one "1,3,2")))
  (is (= 10   (solution-part-one "2,1,3")))
  (is (= 27   (solution-part-one "1,2,3")))
  (is (= 78   (solution-part-one "2,3,1")))
  (is (= 438  (solution-part-one "3,2,1")))
  (is (= 1836 (solution-part-one "3,1,2"))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 620 (solution-part-one problem-input))))

(deftest ^:slow solution-part-two-given-example-inputs-then-example-results
  (is (= 175594  (solution-part-two "0,3,6")))
  (is (= 2578    (solution-part-two "1,3,2")))
  (is (= 3544142 (solution-part-two "2,1,3")))
  (is (= 261214  (solution-part-two "1,2,3")))
  (is (= 6895259 (solution-part-two "2,3,1")))
  (is (= 18      (solution-part-two "3,2,1")))
  (is (= 362     (solution-part-two "3,1,2"))))

(deftest ^:slow solution-part-two-given-problem-input-then-correct-result
  (is (= 110871 (solution-part-two problem-input))))

