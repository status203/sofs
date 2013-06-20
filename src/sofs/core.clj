(ns sofs.core)

(defn longest-count
  "Takes a grid vector consisting of row vectors, and returns the
  length of the longest row"
  [grid]
  (reduce #(max % (count %2))
          0
          grid))
