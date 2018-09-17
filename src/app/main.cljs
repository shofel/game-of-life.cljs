
(ns app.main
  (:require (app.lib :as lib)))

;;;
;; Any live cell with fewer than two live neighbors dies, as if by under population.
;; Any live cell with two or three live neighbors lives on to the next generation.
;; Any live cell with more than three live neighbors dies, as if by overpopulation.
;; Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
;;;

;;;
;; Live:
;; if (< x 2) dead
;; if (<= 2 x 3) live
;; if (> x 3) dead
;;
;; Dead:
;; if (= x 3) live
;;;

(def blinker
  [[0 0 0 0 0]
   [0 0 0 0 0]
   [0 1 1 1 0]
   [0 0 0 0 0]
   [0 0 0 0 0]])

(defn render
  "Pretty print a grid as a table. A row under a row."    
  [grid]
  (reduce #(str %1 "\n" %2) grid))

#_(println (render blinker))

(defn neighbors-coords
  "Given a cell coordinates, return coordinates of it's neighbors."
  [x y]
  (let [coordinates
        [[(dec x) (dec y)] [(dec x) y] [(dec x) (inc y)]
         [(inc x) (dec y)] [(inc x) y] [(inc x) (inc y)]
         [x (dec y)] [x (inc y)]]
        
        non-neg? #(not (neg? %))]
    
    (filter #(every? non-neg? %) coordinates)))

(defn zero-grid
  "Given a grid, make a grid of the same size, but of only zeroes."
  [grid]
  (map #(map (constantly 0) %) grid))

#_(zero-grid blinker)
#_(= (zero-grid [[1 1] [1 0]]) [[0 0] [0 0]])

#_(defn count-neighbors
  "Replace each cell state with the count of it's neighbors."
  [grid]
  ;; Reduce:
  ;;   Start with a zero grid;
  ;;   Account neighbors of the next cell.
  (let [zero-grid (zero-grid grid)])
  (reduce ))

(defn prepare-next-step
  "Prepares data to decide the next step.
   That is transform each cell to a tuple `[alive neighbors-count]`"
  [grid]
  ;; 
  (let []))

;; TODO make a spec:
;;   1 input coordinates is non-negative
;;   2 result does not contain the input
;;   3 result does not contain negative numbers
#_(= (neighbors-coords 4 4) '([3 3] [3 4] [3 5] [5 3] [5 4] [5 5] [4 3] [4 5]))
#_(= (neighbors-coords 0 0) '([1 0] [1 1] [0 1]))

(defn next-step
  "Calculates the next state of grid.
   The naive approach is to create another table with tuples [state, neighbors];
   and then apply the rules to each cell."
  [grid]
  ;; 1 convert cells to [state, neighbors] tuples.

  (let [grid' grid]
    )

  ;;   Loop over cells. For alive cells increase the neighbor counter
  ;;   for each of their neighbors.
  )


;;; The next lines are the original example.

(def a 1)

(defonce b 2)

(defn main! []
  (println "[main]: loading"))

(defn reload! []
  (println "[main] reloaded lib:" lib/c lib/d)
  (println "[main] reloaded:" a b))

(main!)

