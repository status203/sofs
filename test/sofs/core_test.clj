(ns sofs.core-test
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
