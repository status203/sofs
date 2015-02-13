(ns sofs.core-test
  (:require [midje.sweet :refer :all]
            [sofs.core :as sofs])
  (:refer-clojure :exclude [map]))


(tabular
 (facts "about finding the length of the longest row"
        (sofs/longest-count ?grid) => ?length)

 ?grid               ?length
 []                  0
 [[]]                0
 [[1]]               1
 [[1] [2]]           1
 [[1 2] [3]]         2
 [[1] [3 4]]         2)

(tabular
 (facts "about finding the length of the shortest row"
        (sofs/shortest-count ?grid) => ?length)

 ?grid               ?length
 []                  0
 [[]]                0
 [[1]]               1
 [[1] [2]]           1
 [[1 2] [3]]         1
 [[1] [3 4]]         1)

(tabular
 (facts "about finding the longest row"
        (sofs/longest ?grid) => ?seq)

 ?grid               ?seq
 []                  nil
 [[]]                []
 [[1]]               [1]
 [[1] [2]]           [1]
 [[1 2] [3]]         [1 2]
 [[1] [3 4]]         [3 4])

(tabular
 (facts "about finding the shortest row"
        (sofs/shortest ?grid) => ?seq)

 ?grid               ?seq
 []                  nil
 [[]]                []
 [[1]]               [1]
 [[1] [2]]           [1]
 [[1 2] [3]]         [3]
 [[1] [3 4]]         [1])

(fact "about mapping over a single sofs"
        (sofs/map inc [[1] [2 3 4] [5 6]]) => [[2] [3 4 5] [6 7]])
