(ns sofs.core-test
  (:require [midje.sweet :refer :all]
            [sofs.core :refer :all]))


(tabular
 (facts "about finding the length of the longest row"
        (longest-count ?grid) => ?length)

 ?grid               ?length
 []                  0
 [[1]]               1
 [[1] [2]]           1
 [[1 2] [3]]         2
 [[1] [3 4]]         2)
