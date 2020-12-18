(ns advent-of-code-2020.day-14-test
  (:require [advent-of-code-2020.day-14 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n"
               ["mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"
                "mem[8] = 11"
                "mem[7] = 101"
                "mem[8] = 0"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 165 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 14954914379452 (solution-part-one problem-input))))

