(ns sofs.core
  (:require [clojure.core.reducers :as r]))

(defn longest-count
  "Takes a sequence of sequences and returns the length of the longest
  inner sequence. Returns nil if s is empty"
  [s]
  (if (seq s)
      (reduce max (map count s))
      nil))

(defn shortest-count
  "Takes a sequence of sequences and returns the length of the longest
  inner sequence. Returns nil if s is empty"
  [s]
  (if (seq s)
   (reduce min (map count s))
   nil))

(defn longest
  "Takes a sequence of sequences and returns the longest inner sequence.
  Returns nil if sequence is empty."
  [s]
  (if (seq s)
    (reduce #(if (> (count %2) (count %))
               %2
               %))
    nil))

(defn shortest
  "Takes a sequence of sequences and returns the longest inner sequence.
  Returns nil if sequence is empty."
  [s]
  (if (seq s)
    (reduce #(if (< (count %2) (count %))
               %2
               %))
    nil))
