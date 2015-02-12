(ns sofs.core
  (:require [clojure.core.reducers :as r]))

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
