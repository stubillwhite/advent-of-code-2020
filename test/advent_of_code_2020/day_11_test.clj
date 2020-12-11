(ns advent-of-code-2020.day-11-test
  (:require [advent-of-code-2020.day-11 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n"
               ["L.LL.LL.LL"
                "LLLLLLL.LL"
                "L.L.L..L.."
                "LLLL.LL.LL"
                "L.LL.LL.LL"
                "L.LLLLL.LL"
                "..L.L....."
                "LLLLLLLLLL"
                "L.LLLLLL.L"
                "L.LLLLL.LL"]))

(def- expected-states
  (map (partial string/join "\n")
       [["L.LL.LL.LL"
         "LLLLLLL.LL"
         "L.L.L..L.."
         "LLLL.LL.LL"
         "L.LL.LL.LL"
         "L.LLLLL.LL"
         "..L.L....."
         "LLLLLLLLLL"
         "L.LLLLLL.L"
         "L.LLLLL.LL"]

        ["#.##.##.##"
         "#######.##"
         "#.#.#..#.."
         "####.##.##"
         "#.##.##.##"
         "#.#####.##"
         "..#.#....."
         "##########"
         "#.######.#"
         "#.#####.##"]
        
        ["#.LL.L#.##"
         "#LLLLLL.L#"
         "L.L.L..L.."
         "#LLL.LL.L#"
         "#.LL.LL.LL"
         "#.LLLL#.##"
         "..L.L....."
         "#LLLLLLLL#"
         "#.LLLLLL.L"
         "#.#LLLL.##"]
        
        ["#.##.L#.##"
         "#L###LL.L#"
         "L.#.#..#.."
         "#L##.##.L#"
         "#.##.LL.LL"
         "#.###L#.##"
         "..#.#....."
         "#L######L#"
         "#.LL###L.L"
         "#.#L###.##"]
        
        ["#.#L.L#.##"
         "#LLL#LL.L#"
         "L.L.L..#.."
         "#LLL.##.L#"
         "#.LL.LL.LL"
         "#.LL#L#.##"
         "..L.L....."
         "#L#LLLL#L#"
         "#.LLLLLL.L"
         "#.#L#L#.##"]
        
        ["#.#L.L#.##"
         "#LLL#LL.L#"
         "L.#.L..#.."
         "#L##.##.L#"
         "#.#L.LL.LL"
         "#.#L#L#.##"
         "..L.L....."
         "#L#L##L#L#"
         "#.LLLLLL.L"
         "#.#L#L#.##"]]))

(defn- to-string [seating-plan]
  (let [width  (inc (apply max (map first (keys seating-plan))))
        height (inc (apply max (map fnext (keys seating-plan))))]
    (->> (for [y (range height)
               x (range width)]
           (case (get seating-plan [x y])
             :empty-seat    \L
             :occupied-seat \#
             :floor         \.))
         (partition width)
         (map string/join)
         (string/join "\n"))))

(deftest to-string-given-example-input-then-example-input
  (is (= (to-string (parse-input example-input)) example-input)))

(deftest states-given-example-input-then-example-states
  (let [actual-states (->> (parse-input example-input)
                           (states)
                           (take 6)
                           (map to-string))]
    (is (= expected-states actual-states))))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 37  (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 2289 (solution-part-one problem-input))))
