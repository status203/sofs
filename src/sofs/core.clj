(ns sofs.core
  (:refer-clojure :exclude [map reduce partition])
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
    (clojure.core/reduce #(if (> (count %2) (count %))
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
    (clojure.core/reduce #(if (< (count %2) (count %))
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
  ([f sofs] (clojure.core/map #(clojure.core/map f %) sofs))
  ([f sofs1 sofs2] (clojure.core/map #(clojure.core/map f %1 %2) sofs1 sofs2))
  ([f sofs1 sofs2 sofs3 & rst]
   (apply clojure.core/map
          (fn [s1 & rst] (apply clojure.core/map f (conj rst s1)))
          (conj rst sofs3 sofs2 sofs1))))

(defn reduce
  "Returns the result of reducing each row using the function f-row, then
  reducing the results using f-col. f-row and f-col should be functions of two
  arguments and are called in a similar manner to clojure.core/reduce."
  ([f-row f-col sofs]
   (clojure.core/reduce f-col
                        (clojure.core/map (partial clojure.core/reduce f-row) sofs)))
  ([f-row row-val f-col col-val sofs]
   (clojure.core/reduce f-col
                        col-val
                        (clojure.core/map (partial clojure.core/reduce f-row row-val) sofs))))
