(ns advent-of-code-2020.day-3-test
  (:require [advent-of-code-2020.day-3 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n"
               ["..##......."
                "#...#...#.."
                ".#....#..#."
                "..#.#...#.#"
                ".#...##..#."
                "..#.##....."
                ".#.#.#....#"
                ".#........#"
                "#.##...#..."
                "#...##....#"
                ".#..#...#.#"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 7 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 207 (solution-part-one problem-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 336 (solution-part-two example-input))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 2655892800 (solution-part-two problem-input))))
