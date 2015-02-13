(ns sofs.core
  (:require [clojure.core.reducers :as r])
  (:refer-clojure :exclude [map]))

(defn longest-count
  "Takes a sequence of sequences and returns the length of the longest
  inner sequence. Returns nil if s is empty"
  [s]
  (if (seq s)
    (r/fold (r/monoid max (fn [] 0))
              (r/map count s))
      0))

(defn shortest-count
  "Takes a sequence of sequences and returns the length of the longest
  inner sequence. Returns nil if s is empty"
  [s]
  (if (seq s)
      (r/fold (r/monoid min (fn [] Double/POSITIVE_INFINITY))
              (r/map count s))
   0))

(defn longest
  "Takes a sequence of sequences and returns the longest inner sequence.
  Returns nil if sequence is empty."
  [s]
  (if (seq s)
    (reduce #(if (> (count %2) (count %))
               %2
               %)
            (first s)
            (rest s))
    nil))

(defn shortest
  "Takes a sequence of sequences and returns the longest inner sequence.
  Returns nil if sequence is empty."
  [s]
  (if (seq s)
    (reduce #(if (< (count %2) (count %))
               %2
               %)
            (first s)
            (rest s))
    nil))

(defn map
  "Returns a lazy sequence of lazy sequences consisting of the result of
  applying f to the set of first items from a row, set of second items in
  a row, until one of the rows is exhausted. Function f should accept
  number-of-sofs arguments"
  ([f sofs] (clojure.core/map #(clojure.core/map f %) sofs)))
