(ns sofs.core-test
  (:refer-clojure :exclude [map])
  (:require [midje.sweet :refer :all]
            [sofs.core :refer :all]))


(tabular
 (facts "about finding the length of the longest row"
        (longest-count ?grid) => ?length)

 ?grid               ?length
 []                  0
 [[]]                0
 [[1]]               1
 [[1] [2]]           1
 [[1 2] [3]]         2
 [[1] [3 4]]         2)

(tabular
 (facts "about finding the length of the shortest row"
        (shortest-count ?grid) => ?length)

 ?grid               ?length
 []                  0
 [[]]                0
 [[1]]               1
 [[1] [2]]           1
 [[1 2] [3]]         1
 [[1] [3 4]]         1)

(tabular
 (facts "about finding the longest row"
        (longest ?grid) => ?seq)

 ?grid               ?seq
 []                  nil
 [[]]                []
 [[1]]               [1]
 [[1] [2]]           [1]
 [[1 2] [3]]         [1 2]
 [[1] [3 4]]         [3 4])

(tabular
 (facts "about finding the shortest row"
        (shortest ?grid) => ?seq)

 ?grid               ?seq
 []                  nil
 [[]]                []
 [[1]]               [1]
 [[1] [2]]           [1]
 [[1 2] [3]]         [3]
 [[1] [3 4]]         [1])

(fact "about mapping over a single sofs"
      (map inc [[1] [2 3 4] [5 6]]) => [[2] [3 4 5] [6 7]])

(fact "about mapping over 2 sofs"
      (map * [[1] [2 3 4] [5 6]] [[7 6] [5 4 3] [2 1]])
      => [[7] [10 12 12] [10 6]])

(fact "about mapping over 3 sofs"
      (map #(+ (/ %1 %2) %3) [[60]] [[2]] [[3]])
      => [[33]])

(fact "about mapping over 3 sofs"
      (map #(str (+ (/ %1 %2) %3) " " %4) [[60]] [[2]] [[3]] [[\a]])
      => [["33 a"]])

