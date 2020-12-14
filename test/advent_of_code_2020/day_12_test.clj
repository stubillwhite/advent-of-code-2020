(ns advent-of-code-2020.day-12-test
  (:require [advent-of-code-2020.day-12 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n"
               ["F10"
                "N3"
                "F7"
                "R90"
                "F11"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 25 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 381 (solution-part-one problem-input))))
