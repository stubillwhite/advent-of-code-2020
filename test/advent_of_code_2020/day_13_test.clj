(ns advent-of-code-2020.day-13-test
  (:require [advent-of-code-2020.day-13 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input
  (string/join "\n"
               ["939"
                "7,13,x,x,59,x,31,19"]))

(defn- timetable [s]
  (string/join "\n"
               ["23"
                s]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 295 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 2406 (solution-part-one problem-input))))

(deftest solution-part-two-given-example-inputs-then-example-results
  (is (= 1068781    (solution-part-two example-input)))
  (is (= 3417       (solution-part-two (timetable "17,x,13,19"))))
  (is (= 754018     (solution-part-two (timetable "67,7,59,61"))))
  (is (= 779210     (solution-part-two (timetable "67,x,7,59,61"))))
  (is (= 1261476    (solution-part-two (timetable "67,7,x,59,61"))))
  (is (= 1202161486 (solution-part-two (timetable "1789,37,47,1889")))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 225850756401039 (solution-part-two problem-input))))
