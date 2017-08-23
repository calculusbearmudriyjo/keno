(ns keno.module.engine.engine
  (:require [immutant.scheduling :as scheduling]))

(def coefficient-matrix [
                         [0 0  0  0  0   0   1    1    2    2]
                         [3 1  0  0  0   0   0    0    0    0]
                         [0 15 2  1  1   0   0    0    0    0]
                         [0 0  55 10 3   2   2    0    0    0]
                         [0 0  0  80 20  15  4    5    2    0]
                         [0 0  0  0  150 60  20   15   10   5]
                         [0 0  0  0  0   500 80   50   25   30]
                         [0 0  0  0  0   0   1000 200  125  100]
                         [0 0  0  0  0   0   0    2000 1000 300]
                         [0 0  0  0  0   0   0    0    5000 2000]
                         [0 0  0  0  0   0   0    0    0    10000]
                         ])

(defn winner-number [] (vec (take 10 (shuffle (range 81)))))

(defn count-coincidence [bet-seq winner-seq] (reduce #(if (contains? bet-seq %2) (+ 1 %1) %1) 0 winner-seq))

(defn calculate-bet [cost bet-seq winner-seq] (* cost (nth (nth coefficient-matrix (count bet-seq)) (count-coincidence bet-seq winner-seq))))
