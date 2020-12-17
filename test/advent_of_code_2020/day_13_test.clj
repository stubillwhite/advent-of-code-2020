(ns advent-of-code-2020.day-13-test
  (:require [advent-of-code-2020.day-13 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input
  (string/join "\n"
               ["939"
                "7,13,x,x,59,x,31,19"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 295 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 2406 (solution-part-one problem-input))))

