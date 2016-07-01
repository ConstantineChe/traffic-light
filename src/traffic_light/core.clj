(ns traffic-light.core
  (:require [traffic-light.watches :refer [red]])
  (:import [java.lang Thread])
  (:gen-class))



(defn red-light []
  (doseq [i (reverse (range 4 23))]
         (prn i)
         (prn "red")
         (Thread/sleep 1000)))


(defn red->yellow []
  (doseq [_ (range 4)]
    (prn "red")
    (prn "yellow")
    (Thread/sleep 1000)))

(defn green->yellow []
  (doseq [_ (range 4)]
    (prn "yellow")
    (Thread/sleep 1000)))

(defn green-light []
  (doseq [i (reverse (range 1 23))]
    (prn i)
    (prn "green")
    (Thread/sleep 1000)))

(defn start-traffic []
  (doseq [light (cycle [red-light red->yellow green-light green->yellow])]
    (light)))

(defn -main [& [arg]]
  (if (= "w" arg)
    (swap! red update :count dec)
    (start-traffic)))
